package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.client.structures.result.RedisResultBase;
import jdbc.client.structures.result.SimpleType;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import jdbc.types.RedisColumnTypeHelper;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisMapResultSet extends RedisResultSetBase<SimpleType<?>, Map<String, Object>, Map.Entry<String, Object>> {

    private static final String FIELD = "field";

    public RedisMapResultSet(RedisStatement statement, @NotNull RedisMapResult result) {
        super(statement, result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull RedisResultBase<SimpleType<?>, Map<String, Object>> result) {
        return Arrays.asList(createColumn(FIELD, RedisColumnTypeHelper.STRING), createColumn(VALUE, result.getType()));
    }

    @Override
    protected @NotNull List<Map.Entry<String, Object>> createRows(@NotNull Map<String, Object> result) {
        return new ArrayList<>(result.entrySet());
    }

    @Override
    protected Object getResultObject(@NotNull Map.Entry<String, Object> row, String columnLabel) throws SQLException {
        int columnIndex = findResultColumn(columnLabel);
        return columnIndex == 1 ? row.getKey() : columnIndex == 2 ? row.getValue() : null;
    }
}