package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.ColumnHint;
import jdbc.client.structures.result.RedisListResult;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisListResultSet extends RedisResultSetBase<Object> {

    public RedisListResultSet(RedisStatement statement, @NotNull RedisListResult result) {
        super(statement, createMetaData(result), result.getResult(), result.getColumnHint());
    }

    private static RedisResultSetMetaData createMetaData(@NotNull RedisListResult result) {
        return new RedisResultSetMetaData(createColumns(result));
    }

    private static List<ColumnMetaData> createColumns(@NotNull RedisListResult result) {
        ColumnHint columnHint = result.getColumnHint();
        ColumnMetaData defaultColumn = createDefaultColumn(result.getType());
        if (columnHint == null) return Collections.singletonList(defaultColumn);
        ColumnMetaData hintColumn = createHintColumn(columnHint);
        return hintColumn.getName().equals(defaultColumn.getName())
                ? Arrays.asList(hintColumn, createColumn(result.getCommand(), result.getType()))
                : Arrays.asList(hintColumn, defaultColumn);
    }

    private static ColumnMetaData createDefaultColumn(@NotNull String type) {
        return createColumn(VALUE, type);
    }

    @Override
    protected Object getObject(@NotNull Object row, String columnLabel) throws SQLException {
        int columnIndex = getMetaData().findColumn(columnLabel);
        return columnIndex == 1 ? row : null;
    }
}
