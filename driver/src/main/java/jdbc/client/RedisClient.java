package jdbc.client;

import jdbc.client.structures.result.RedisResult;

import java.sql.SQLException;

public interface RedisClient extends AutoCloseable {

    RedisResult<?, ?> execute(String sql) throws SQLException;

    void setDatabase(String db) throws SQLException;

    String getDatabase();

    void close() throws SQLException;
}