package jdbc.client.impl.cluster;

import jdbc.client.impl.RedisClientBase;
import jdbc.client.impl.standalone.RedisJedisClient;
import jdbc.client.structures.query.NodeHint;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RedisJedisClusterClient extends RedisClientBase {

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
    public synchronized Object executeImpl(@NotNull RedisQuery query) {
        Protocol.Command command = query.getCommand();
        String[] params = query.getParams();
        return query.isBlocking()
                ? jedisCluster.sendBlockingCommand(command, params)
                : jedisCluster.sendCommand(command, params);
    }

    @Override
    protected String setDatabase(int index) {
        if (index == 0) return "OK";
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