package jdbc.client;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;
import java.util.Properties;

public class RedisSingleClient extends RedisClientBase {

    private final Jedis jedis;

    public RedisSingleClient(String url, Properties info) throws SQLException {
        try {
            RedisURI uri = new RedisURI(url, info);
            jedis = new Jedis(uri.getHostAndPort(), uri);
            jedis.connect();
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    protected synchronized Object execute(@NotNull RedisQuery query) {
        return jedis.sendCommand(query.getCommand(), query.getParams());
    }

    @Override
    protected synchronized String setDatabase(int index) {
        return jedis.select(index);
    }

    @Override
    public synchronized @NotNull String getDatabase() {
        return Integer.toString(jedis.getDB());
    }

    @Override
    protected synchronized void doClose() {
        jedis.close();
    }

}
