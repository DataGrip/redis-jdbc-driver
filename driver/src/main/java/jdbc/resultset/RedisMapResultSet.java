package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.ColumnHint;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisMapResultSet extends RedisResultSetBase<Map.Entry<String, Object>> {

    private static final String FIELD = "field";

    public RedisMapResultSet(RedisStatement statement, @NotNull RedisMapResult result) {
        super(statement, createMetaData(result), createRows(result.getResult()), result.getColumnHint());
    }

    private static RedisResultSetMetaData createMetaData(@NotNull RedisMapResult result) {
        return new RedisResultSetMetaData(createColumns(result));
    }

    private static List<ColumnMetaData> createColumns(@NotNull RedisMapResult result) {
        ColumnHint columnHint = result.getColumnHint();
        List<ColumnMetaData> defaultColumns = createDefaultColumns(result.getType());
        if (columnHint == null) return defaultColumns;
        return new ArrayList<>() {{ add(createHintColumn(columnHint)); addAll(defaultColumns); }};
    }

    private static List<ColumnMetaData> createDefaultColumns(@NotNull String type) {
        return Arrays.asList(createColumn(FIELD, "string"), createColumn(VALUE, type));
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