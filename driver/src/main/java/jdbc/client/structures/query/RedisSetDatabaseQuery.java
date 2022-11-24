package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisSetDatabaseQuery extends RedisQuery {
    private final int dbIndex;

    public RedisSetDatabaseQuery(@NotNull CompositeCommand compositeCommand, int dbIndex, @Nullable ColumnHint columnHint) {
        super(compositeCommand, new String[]{Integer.toString(dbIndex)}, columnHint);
        this.dbIndex = dbIndex;
    }

    public int getDbIndex() {
        return dbIndex;
    }
}
