package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.result.RedisListResult;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisListResultSet extends RedisResultSetBase<Object> {

    public RedisListResultSet(RedisStatement statement, RedisListResult result) {
        super(statement, createMetaData(result.getType()), result.getResult(), result.getColumnHint());
    }

    private static RedisResultSetMetaData createMetaData(String type) {
        return new RedisResultSetMetaData(createColumn("value", type));
    }

    @Override
    protected Object getObject(@NotNull Object row, String columnLabel) throws SQLException {
        int columnIndex = getMetaData().findColumn(columnLabel);
        return columnIndex == 1 ? row : null;
    }
}
