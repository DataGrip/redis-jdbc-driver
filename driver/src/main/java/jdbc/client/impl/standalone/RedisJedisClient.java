package jdbc.client.impl.standalone;

import jdbc.client.impl.RedisClientBase;
import jdbc.client.structures.query.RedisBlockingQuery;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

public class RedisJedisClient extends RedisClientBase {

    private final Jedis jedis;

    public RedisJedisClient(@NotNull RedisJedisURI uri) throws SQLException {
        try {
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
    protected synchronized Object execute(@NotNull RedisBlockingQuery query) {
        return jedis.sendBlockingCommand(query.getCommand(), query.getParams());
    }

    @Override
    protected synchronized String setDatabase(int index) {
        return jedis.select(index);
    }

    @Override
    public synchronized String getDatabase() {
        return Integer.toString(jedis.getDB());
    }

    @Override
    protected synchronized void doClose() {
        jedis.close();
    }

}
