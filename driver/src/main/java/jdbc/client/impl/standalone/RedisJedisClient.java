package jdbc.client.impl.standalone;

import jdbc.client.RedisMode;
import jdbc.client.impl.RedisClientBase;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

public class RedisJedisClient extends RedisClientBase {

    private final Jedis jedis;

    public RedisJedisClient(@NotNull RedisJedisURI uri) throws SQLException {
        try {
            jedis = new Jedis(uri.getHostAndPort(), uri);
            jedis.connect();
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }

    public RedisJedisClient(@NotNull Connection connection) throws SQLException {
        try {
            jedis = new Jedis(connection);
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }


    @Override
    protected synchronized Object executeImpl(@NotNull RedisQuery query) {
        Command command = query.getCommand();
        String[] params = query.getParams();
        return query.isBlocking()
                ? jedis.sendBlockingCommand(command, params)
                : jedis.sendCommand(command, params);
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



    @Override
    public @NotNull RedisMode getMode() {
        return RedisMode.STANDALONE;
    }
}
