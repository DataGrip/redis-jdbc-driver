package jdbc.client;

import jdbc.client.structures.query.RedisQuery;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;
import java.util.Properties;

public class RedisClusterClient extends RedisClientBase {

    private final JedisCluster jedisCluster;

    public RedisClusterClient(String url, Properties info) throws SQLException {
        try {
            RedisURI uri = new RedisURI(url, info);
            jedisCluster = new JedisCluster(uri.getHostAndPort(), uri, 1, new GenericObjectPoolConfig<>());
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected synchronized Object execute(@NotNull RedisQuery query) {
        return jedisCluster.sendCommand(query.getCommand(), query.getParams());
    }

    // TODO: think about return value + JedisException?
    @Override
    protected String setDatabase(int index) {
        if (index != 0) throw new JedisException(String.format("Cluster contains only database 0: %s.", index));
        return null;
    }

    @Override
    public @NotNull String getDatabase() {
        return "0";
    }

    @Override
    public synchronized void doClose() {
        jedisCluster.close();
    }
}
