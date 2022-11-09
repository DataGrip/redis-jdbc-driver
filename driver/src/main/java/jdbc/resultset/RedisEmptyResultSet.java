package jdbc.resultset;

import jdbc.RedisStatement;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collections;

public class RedisEmptyResultSet extends RedisResultSetBase<Void> {

    public RedisEmptyResultSet(RedisStatement statement) {
        super(statement, createMetaData(), Collections.emptyList(), null);
    }

    private static RedisResultSetMetaData createMetaData() {
        return new RedisResultSetMetaData();
    }

    @Override
    protected Object getObject(@NotNull Void row, String columnLabel) throws SQLException {
        return null;
    }
}
