package jdbc.client.impl.cluster;

import jdbc.client.RedisMode;
import jdbc.client.impl.RedisClientBase;
import jdbc.client.impl.RedisJedisURIBase.CompleteHostAndPortMapper;
import jdbc.client.impl.standalone.RedisJedisClient;
import jdbc.client.structures.RedisCommand;
import jdbc.client.structures.RedisCommands;
import jdbc.client.structures.query.NodeHint;
import jdbc.client.structures.query.RedisKeyPatternQuery;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.*;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.JedisClusterHashTag;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class RedisJedisClusterClient extends RedisClientBase {

    private static final Set<RedisCommand> UNSUPPORTED_COMMANDS = Set.of(RedisCommands.DBSIZE, RedisCommands.WAIT);


    private final JedisCluster jedisCluster;

    public RedisJedisClusterClient(@NotNull RedisJedisClusterURI uri) throws SQLException {
        try {
            jedisCluster = new JedisCluster(uri.getNodes(), uri, uri.getMaxAttempts(), new SingleConnectionPoolConfig());
            checkClusterNodes(jedisCluster, uri.getHostAndPortMapper());
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }

    private static void checkClusterNodes(@NotNull JedisCluster cluster,
                                          @Nullable CompleteHostAndPortMapper hostAndPortMapper) throws JedisConnectionException {
        if (hostAndPortMapper == null) return;
        Iterable<String> nodeKeys = cluster.getClusterNodes().keySet();
        for (String nodeKey : nodeKeys) {
            HostAndPort nodeHostAndPort = HostAndPort.from(nodeKey);
            hostAndPortMapper.getHostAndPort(nodeHostAndPort);
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
        Connection nodeConnection = getNodeConnection(nodeHostAndPort);
        try (RedisJedisClient nodeClient = new RedisJedisClient(nodeConnection)) {
            return nodeClient.execute(query);
        }
    }

    private synchronized @NotNull Connection getNodeConnection(@NotNull HostAndPort nodeHostAndPort) throws SQLException {
        String nodeKey = JedisClusterInfoCache.getNodeKey(nodeHostAndPort);
        Map<String, ConnectionPool> nodes = jedisCluster.getClusterNodes();
        ConnectionPool nodePool = nodes.get(nodeKey);
        Connection nodeConnection = nodePool != null ? nodePool.getResource() : null;
        if (nodeConnection == null)
            throw new SQLException(String.format("Cluster node not found: %s.", nodeHostAndPort));
        return nodeConnection;
    }


    @Override
    protected Object executeImpl(@NotNull RedisKeyPatternQuery query) throws SQLException {
        checkSupportInClusterMode(query);
        return super.executeImpl(query);
    }

    private static void checkSupportInClusterMode(@NotNull RedisKeyPatternQuery query) throws SQLException {
        String keyPattern = query.getKeyPattern();
        if (keyPattern == null || !JedisClusterHashTag.isClusterCompliantMatchPattern(keyPattern))
            throw new SQLException(String.format("Cluster mode only supports the %s command"
                    + " with a pattern containing a hash-tag (curly-brackets enclosed string).", query.getCommand()));
    }


    @Override
    protected synchronized Object executeImpl(@NotNull RedisQuery query) throws SQLException {
        checkSupportInClusterMode(query);
        String sampleKey = query.getSampleKey();
        ProtocolCommand cmd = query.getRawCommand();
        String[] args = query.getRawParams();
        if (query.isBlocking()) {
            return sampleKey != null
                    ? jedisCluster.sendBlockingCommand(sampleKey, cmd, args)
                    : jedisCluster.sendBlockingCommand(cmd, args);
        } else {
            return sampleKey != null
                    ? jedisCluster.sendCommand(sampleKey, cmd, args)
                    : jedisCluster.sendCommand(cmd, args);
        }
    }

    private static void checkSupportInClusterMode(@NotNull RedisQuery query) throws SQLException {
        RedisCommand command = query.getCommand();
        if (UNSUPPORTED_COMMANDS.contains(command))
            throw new SQLException(String.format("Cluster mode does not support the %s command.", command));
    }


    @Override
    protected String setDatabase(int index) {
        if (index == 0) return "OK";
        throw new JedisDataException("ERR DB index is out of range");
    }

    @Override
    public String getDatabase() {
        return "0";
    }


    @Override
    public synchronized void doClose() {
        jedisCluster.close();
    }


    @Override
    public @NotNull RedisMode getMode() {
        return RedisMode.CLUSTER;
    }
}