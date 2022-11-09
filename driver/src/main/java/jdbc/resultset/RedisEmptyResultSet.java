package jdbc.resultset;

import jdbc.RedisStatement;
import jdbc.client.structures.query.RedisQuery;
import jdbc.resultset.RedisResultSetMetaData.ColumnMetaData;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class RedisEmptyResultSet extends RedisResultSetBase<String, Void, Void> {

    public RedisEmptyResultSet(RedisStatement statement) {
        super(statement);
    }

    @Override
    protected @NotNull List<ColumnMetaData> createResultColumns(@NotNull RedisQuery query,
                                                                @NotNull String type,
                                                                @NotNull Void result) {
        return Collections.emptyList();
    }

    @Override
    protected @NotNull List<Void> createRows(@NotNull Void result) {
        return Collections.emptyList();
    }

    @Override
    protected Object getObject(@NotNull Void row, String columnLabel) throws SQLException {
        return null;
    }
}
