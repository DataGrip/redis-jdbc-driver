package jdbc.client.impl.cluster;

import jdbc.client.impl.RedisClientBase;
import jdbc.client.impl.standalone.RedisJedisClient;
import jdbc.client.structures.query.NodeHint;
import jdbc.client.structures.query.RedisKeysPatternQuery;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.*;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.JedisClusterHashTag;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisJedisClusterClient extends RedisClientBase {

    private static final String UNSUPPORTED_COMMAND_MESSAGE = "Cluster mode does not support %s command";
    private static final String UNSUPPORTED_KEYS_PATTERN_COMMAND_MESSAGE = "Cluster mode only supports %s command"
            + " with pattern containing hash-tag ( curly-brackets enclosed string )";

    private static final Set<Command> UNSUPPORTED_COMMANDS = Set.of(Command.DBSIZE, Command.WAIT);


    private final JedisCluster jedisCluster;
    private final Map<String, RedisJedisClient> jedisClusterNodes = new HashMap<>();

    public RedisJedisClusterClient(@NotNull RedisJedisClusterURI uri) throws SQLException {
        try {
            jedisCluster = new JedisCluster(uri.getNodes(), uri, uri.getMaxAttempts(), new SingleConnectionPoolConfig());
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public Object execute(@NotNull RedisQuery query) throws SQLException {
        NodeHint nodeHint = query.getNodeHint();
        if (nodeHint != null) return execute(nodeHint.getHostAndPort(), query);
        return super.execute(query);
    }

    private synchronized Object execute(@NotNull HostAndPort nodeHostAndPort,
                                        @NotNull RedisQuery query) throws SQLException {
        String nodeKey = JedisClusterInfoCache.getNodeKey(nodeHostAndPort);
        RedisJedisClient jedisNode = jedisClusterNodes.get(nodeKey);
        if (jedisNode == null) {
            Map<String, ConnectionPool> nodes = jedisCluster.getClusterNodes();
            ConnectionPool nodePool = nodes.get(nodeKey);
            Connection nodeConnection = nodePool != null ? nodePool.getResource() : null;
            if (nodeConnection == null)
                throw new SQLException(String.format("Cluster node not found: %s", nodeHostAndPort));
            jedisNode = new RedisJedisClient(nodeConnection);
            jedisClusterNodes.put(nodeKey, jedisNode);
        }
        return jedisNode.execute(query);
    }


    @Override
    protected Object executeImpl(@NotNull RedisKeysPatternQuery query) throws SQLException {
        checkSupportInClusterMode(query);
        return super.executeImpl(query);
    }

    private static void checkSupportInClusterMode(@NotNull RedisKeysPatternQuery query) throws SQLException {
        String keysPattern = query.getKeysPattern();
        if (keysPattern == null || !JedisClusterHashTag.isClusterCompliantMatchPattern(keysPattern))
            throw new SQLException(String.format(UNSUPPORTED_KEYS_PATTERN_COMMAND_MESSAGE, query.getCommand()));
    }


    @Override
    protected synchronized Object executeImpl(@NotNull RedisQuery query) throws SQLException {
        checkSupportInClusterMode(query);
        String sampleKey = query.getSampleKey();
        Command command = query.getCommand();
        String[] params = query.getParams();
        if (query.isBlocking()) {
            return sampleKey != null
                    ? jedisCluster.sendBlockingCommand(sampleKey, command, params)
                    : jedisCluster.sendBlockingCommand(command, params);
        } else {
            return sampleKey != null
                    ? jedisCluster.sendCommand(sampleKey, command, params)
                    : jedisCluster.sendCommand(command, params);
        }
    }

    private static void checkSupportInClusterMode(@NotNull RedisQuery query) throws SQLException {
        Command command = query.getCommand();
        if (UNSUPPORTED_COMMANDS.contains(command))
            throw new SQLException(String.format(UNSUPPORTED_COMMAND_MESSAGE, command));
    }


    @Override
    protected String setDatabase(int index) {
        if (index == 0) return "OK";
        // TODO (cluster): unify
        throw new JedisDataException("ERR SELECT is not allowed in cluster mode");
    }

    @Override
    public String getDatabase() {
        return "0";
    }


    @Override
    public synchronized void doClose() {
        jedisCluster.close();
    }
}