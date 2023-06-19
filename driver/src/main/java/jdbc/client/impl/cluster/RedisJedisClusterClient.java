package jdbc.client.impl.cluster;

import jdbc.client.impl.RedisClientBase;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

public class RedisJedisClusterClient extends RedisClientBase {

    private final JedisCluster jedisCluster;

    public RedisJedisClusterClient(@NotNull RedisJedisClusterURI uri) throws SQLException {
        try {
            jedisCluster = new JedisCluster(uri.getNodes(), uri, uri.getMaxAttempts(), new SingleConnectionPoolConfig());
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected synchronized Object execute(@NotNull RedisQuery query) {
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