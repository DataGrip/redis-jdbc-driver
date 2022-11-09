package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.ColumnHint;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisListResult;
import jdbc.client.structures.result.RedisResultBase;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static jdbc.Utils.toLowerCase;
import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisListResultSet extends RedisResultSetBase<String, List<Object>, Object> {

    public RedisListResultSet(RedisStatement statement, @NotNull RedisListResult result) {
        super(statement, result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createColumns(@NotNull RedisResultBase<String, List<Object>> result) {
        RedisQuery query = result.getQuery();
        ColumnHint columnHint = query.getColumnHint();
        if (columnHint != null && columnHint.getName().equals(VALUE)) {
            String command = toLowerCase(query.getCommand().name());
            return Arrays.asList(createHintColumn(columnHint), createColumn(command, result.getType()));
        }
        return super.createColumns(result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull RedisQuery query,
                                                                @NotNull String type,
                                                                @NotNull List<Object> result) {
        return Collections.singletonList(createColumn(VALUE, type));
    }

    @Override
    protected @NotNull List<Object> createRows(@NotNull List<Object> result) {
        return result;
    }

    @Override
    protected Object getObject(@NotNull Object row, String columnLabel) throws SQLException {
        int columnIndex = getMetaData().findColumn(columnLabel);
        return columnIndex == 1 ? row : null;
    }
}
