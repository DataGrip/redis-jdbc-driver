package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.result.RedisMapResult;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisMapResultSet extends RedisResultSetBase<Map.Entry<String, Object>> {

    public RedisMapResultSet(RedisStatement statement, RedisMapResult result) {
        super(statement, createMetaData(result.getType()), createRows(result.getResult()));
    }

    private static RedisResultSetMetaData createMetaData(String type) {
        return new RedisResultSetMetaData(createColumn("field", "string"), createColumn("value", type));
    }

    private static List<Map.Entry<String, Object>> createRows(Map<String, Object> result) {
        return new ArrayList<>(result.entrySet());
    }

    @Override
    protected Object getObject(@NotNull Map.Entry<String, Object> row, String columnLabel) throws SQLException {
        int columnIndex = getMetaData().findColumn(columnLabel);
        return columnIndex == 1 ? row.getKey() : columnIndex == 2 ? row.getValue() : null;
    }
}