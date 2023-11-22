package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.query.structures.ColumnHint;
import jdbc.client.query.structures.RedisQuery;
import jdbc.client.result.structures.RedisListResult;
import jdbc.client.result.structures.RedisResultBase;
import jdbc.client.result.structures.SimpleType;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;
import static jdbc.utils.Utils.getColumnTitle;

public class RedisListResultSet extends RedisResultSetBase<SimpleType<?>, List<Object>, Object> {

    public RedisListResultSet(RedisStatement statement, @NotNull RedisListResult result) {
        super(statement, result);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull RedisResultBase<SimpleType<?>, List<Object>> result) {
        RedisQuery query = result.getQuery();
        ColumnHint columnHint = query.getColumnHint();
        String resultColumnName = VALUE;
        if (columnHint != null && columnHint.getName().equals(resultColumnName)) {
            resultColumnName = getColumnTitle(query.getCommand());
        }
        return Collections.singletonList(createColumn(resultColumnName, result.getType()));
    }

    @Override
    protected @NotNull List<Object> createRows(@NotNull List<Object> result) {
        return result;
    }

    @Override
    protected Object getResultObject(@NotNull Object row, String columnLabel) throws SQLException {
        int columnIndex = findResultColumn(columnLabel);
        return columnIndex == 1 ? row : null;
    }
}
