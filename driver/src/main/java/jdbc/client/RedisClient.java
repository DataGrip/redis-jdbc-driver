package jdbc.client;

import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public interface RedisClient extends AutoCloseable {

    @NotNull RedisResult execute(@Nullable String sql) throws SQLException;

    void setDatabase(@Nullable String db) throws SQLException;

    @NotNull String getDatabase();

    void close() throws SQLException;
}