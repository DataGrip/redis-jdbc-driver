package jdbc.client.impl;

import jdbc.client.RedisClient;
import jdbc.client.helpers.query.RedisQueryHelper;
import jdbc.client.helpers.result.RedisResultHelper;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.query.RedisSetDatabaseQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.sql.SQLException;

import static jdbc.utils.Utils.parseSqlDbIndex;

public abstract class RedisClientBase implements RedisClient {

    @Override
    public final RedisResult execute(String sql) throws SQLException {
        try {
            RedisQuery query = RedisQueryHelper.parseQuery(sql);
            Object data = execute(query);
            return RedisResultHelper.parseResult(query, data);
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    public Object execute(@NotNull RedisQuery query) throws SQLException {
       if (query instanceof RedisSetDatabaseQuery) return executeImpl((RedisSetDatabaseQuery)query);
       return executeImpl(query);
    }

    protected abstract Object executeImpl(@NotNull RedisQuery query) throws SQLException;

    private Object executeImpl(@NotNull RedisSetDatabaseQuery query) {
        return setDatabase(query.getDbIndex());
    }

    @Override
    public final void setDatabase(String db) throws SQLException {
        try {
            setDatabase(parseSqlDbIndex(db));
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    protected abstract String setDatabase(int index);

    @Override
    public final void close() throws SQLException {
        try {
            doClose();
        } catch (JedisException e) {
            throw new SQLException(e);
        }
    }

    protected abstract void doClose();


    protected static class SingleConnectionPoolConfig extends ConnectionPoolConfig {
        public SingleConnectionPoolConfig() {
            super();
            setMaxTotal(1);
        }
    }
}
