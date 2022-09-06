package jdbc.resultset;

import jdbc.RedisStatement;

import java.sql.SQLException;
import java.util.Collections;

public class RedisEmptyResultSet extends RedisResultSetBase<Void> {

    public RedisEmptyResultSet(RedisStatement statement) {
        super(statement, createMetaData(), Collections.emptyList());
    }

    private static RedisResultSetMetaData createMetaData() {
        return new RedisResultSetMetaData();
    }

    @Override
    protected Object getObject(Void row, String columnLabel) throws SQLException {
        throw new SQLException("Exhausted ResultSet.");
    }
}
