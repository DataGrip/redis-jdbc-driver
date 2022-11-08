package jdbc.client;

import jdbc.client.structures.query.RedisQuery;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

class RedisJedisClusterClient extends RedisClientBase {

    private final JedisCluster jedisCluster;

    public RedisJedisClusterClient(@NotNull RedisJedisClusterURI uri) throws SQLException {
        try {
            jedisCluster = new JedisCluster(uri.getNodes(), uri, uri.getMaxAttempts(), new GenericObjectPoolConfig<>());
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected synchronized Object execute(@NotNull RedisQuery query) {
        return jedisCluster.sendCommand(query.getCommand(), query.getParams());
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