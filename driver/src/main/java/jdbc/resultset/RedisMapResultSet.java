package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisMapResultSet extends RedisResultSetBase<String, Map<String, Object>, Map.Entry<String, Object>> {

    private static final String FIELD = "field";

    public RedisMapResultSet(RedisStatement statement, @NotNull RedisMapResult result) {
        super(statement, result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull RedisQuery query,
                                                                @NotNull String type,
                                                                @NotNull Map<String, Object> result) {
        return Arrays.asList(createColumn(FIELD, "string"), createColumn(VALUE, type));
    }

    @Override
    protected @NotNull List<Map.Entry<String, Object>> createRows(@NotNull Map<String, Object> result) {
        return new ArrayList<>(result.entrySet());
    }

    @Override
    protected Object getObject(@NotNull Map.Entry<String, Object> row, String columnLabel) throws SQLException {
        int columnIndex = getMetaData().findColumn(columnLabel);
        return columnIndex == 1 ? row.getKey() : columnIndex == 2 ? row.getValue() : null;
    }
}