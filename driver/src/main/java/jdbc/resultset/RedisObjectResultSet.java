package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.result.RedisObjectResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisObjectResultSet extends RedisResultSetBase<Map<String, String>, List<Map<String, Object>>, Map<String, Object>> {

    public RedisObjectResultSet(RedisStatement statement, @NotNull RedisObjectResult result) {
        super(statement, result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull Map<String, String> type) {
        return type.entrySet().stream().map(e -> createColumn(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    @Override
    protected @NotNull List<Map<String, Object>> createRows(@NotNull List<Map<String, Object>> result) {
        return result;
    }

    @Override
    protected Object getObject(@NotNull Map<String, Object> row, String columnLabel) throws SQLException {
        return row.get(columnLabel);
    }
}
