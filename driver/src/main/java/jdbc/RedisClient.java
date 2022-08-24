package jdbc;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RedisClient implements Client {

    private final Jedis jedis;

    public RedisClient(String url, Properties info) throws SQLException {
        try {
            RedisURI uri = new RedisURI(url, info);
            jedis = new Jedis(uri.getHostAndPort(), uri);
            jedis.connect();
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSet execute(String sql) throws SQLException {
        try {
            RedisQuery query = RedisClientUtil.parseQuery(sql);
            Object result = execute(query);
            return RedisClientUtil.createResultSet(query, result);
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    private synchronized Object execute(RedisQuery query) {
        Protocol.Command command = query.getCommand();
        String[] params = query.getParams();
        return params == null ? jedis.sendCommand(command) : jedis.sendCommand(command, params);
    }

    @Override
    public void setDatabase(String db) throws SQLException {
        try {
            setDatabase(Integer.parseInt(db));
        } catch (NumberFormatException e) {
            throw new SQLException(String.format("Database should be a number: %s.", db));
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    private synchronized void setDatabase(int index) {
        jedis.select(index);
    }

    @Override
    public synchronized String getDatabase() {
        return Integer.toString(jedis.getDB());
    }

    @Override
    public void close() throws SQLException {
        try {
            jedis.close();
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }
}
