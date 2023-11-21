package jdbc.client.impl.standalone;

import jdbc.client.RedisMode;
import jdbc.client.impl.RedisClientBase;
import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.commands.ProtocolCommand;
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
        ProtocolCommand cmd = query.getRawCommand();
        String[] args = query.getRawParams();
        return query.isBlocking()
                ? jedis.sendBlockingCommand(cmd, args)
                : jedis.sendCommand(cmd, args);
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
