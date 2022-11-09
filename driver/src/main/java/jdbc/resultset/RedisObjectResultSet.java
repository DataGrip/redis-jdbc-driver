package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.ColumnHint;
import jdbc.client.structures.result.RedisObjectResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisObjectResultSet extends RedisResultSetBase<Map<String, Object>> {

    public RedisObjectResultSet(RedisStatement statement, @NotNull RedisObjectResult result) {
        super(statement, createMetaData(result), result.getResult(), result.getColumnHint());
    }

    private static RedisResultSetMetaData createMetaData(@NotNull RedisObjectResult result) {
        return new RedisResultSetMetaData(createColumns(result));
    }

    private static List<ColumnMetaData> createColumns(@NotNull RedisObjectResult result) {
        ColumnHint columnHint = result.getColumnHint();
        List<ColumnMetaData> defaultColumns = createDefaultColumns(result.getType());
        if (columnHint == null) return defaultColumns;
        return new ArrayList<>() {{ add(createHintColumn(columnHint)); addAll(defaultColumns); }};
    }

    private static List<ColumnMetaData> createDefaultColumns(@NotNull Map<String, String> type) {
        return type.entrySet().stream().map(e -> createColumn(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    @Override
    protected Object getObject(@NotNull Map<String, Object> row, String columnLabel) throws SQLException {
        return row.get(columnLabel);
    }
}
