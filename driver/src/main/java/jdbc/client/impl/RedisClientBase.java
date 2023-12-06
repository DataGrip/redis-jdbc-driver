package jdbc.client.impl;

import jdbc.client.RedisClient;
import jdbc.client.query.RedisQueryHelper;
import jdbc.client.query.structures.RedisKeyPatternQuery;
import jdbc.client.query.structures.RedisQuery;
import jdbc.client.query.structures.RedisSetDatabaseQuery;
import jdbc.client.result.RedisResultHelper;
import jdbc.client.result.structures.RedisResult;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.SafeEncoder;

import java.sql.SQLException;

import static jdbc.utils.Utils.parseSqlDbIndex;

public abstract class RedisClientBase implements RedisClient {

    @Override
    public final RedisResult execute(String sql) throws SQLException {
        try {
            RedisQuery query = RedisQueryHelper.parseQuery(sql);
            Object data = execute(query);
            return RedisResultHelper.parseResult(data, query);
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }

    public Object execute(@NotNull RedisQuery query) throws SQLException {
       if (query instanceof RedisSetDatabaseQuery) return executeImpl((RedisSetDatabaseQuery) query);
       if (query instanceof RedisKeyPatternQuery) return executeImpl((RedisKeyPatternQuery) query);
       return executeImpl(query);
    }


    private Object executeImpl(@NotNull RedisSetDatabaseQuery query) {
        String response = setDatabase(query.getDbIndex());
        return response != null ? SafeEncoder.encode(response) : null;
    }

    protected Object executeImpl(@NotNull RedisKeyPatternQuery query) throws SQLException {
        return executeImpl((RedisQuery) query);
    }

    protected abstract Object executeImpl(@NotNull RedisQuery query) throws SQLException;


    @Override
    public final void setDatabase(String db) throws SQLException {
        try {
            setDatabase(parseSqlDbIndex(db));
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }

    protected abstract String setDatabase(int index);


    @Override
    public final void close() throws SQLException {
        try {
            doClose();
        } catch (JedisException e) {
            throw sqlWrap(e);
        }
    }

    protected abstract void doClose();


    protected static SQLException sqlWrap(@NotNull JedisException e) {
        if (e.getCause() == null) {
            Throwable[] suppressed = e.getSuppressed();
            if (suppressed != null && suppressed.length == 1) {
                return new SQLException(e.getMessage(), suppressed[0]);
            }
        }
        return new SQLException(e);
    }


    protected static class SingleConnectionPoolConfig extends ConnectionPoolConfig {
        public SingleConnectionPoolConfig() {
            super();
            setMaxTotal(1);
        }
    }
}
