package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.result.RedisObjectResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisObjectResultSet extends RedisResultSetBase<Map<String, Object>> {

    public RedisObjectResultSet(RedisStatement statement, RedisObjectResult result) {
        super(statement, createMetaData(result.getType()), result.getResult());
    }

    private static RedisResultSetMetaData createMetaData(Map<String, String> type) {
        return new RedisResultSetMetaData(
                type.entrySet().stream().map(e -> createColumn(e.getKey(), e.getValue())).toArray(ColumnMetaData[]::new)
        );
    }

    @Override
    protected Object getObject(@NotNull Map<String, Object> row, String columnLabel) throws SQLException {
        return row.get(columnLabel);
    }
}
