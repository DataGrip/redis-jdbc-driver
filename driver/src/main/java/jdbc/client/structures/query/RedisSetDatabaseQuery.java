package jdbc.client.structures.query;

import jdbc.client.structures.RedisCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisSetDatabaseQuery extends RedisQuery {

    private final int dbIndex;

    public RedisSetDatabaseQuery(@NotNull RedisCommand command,
                                 int dbIndex,
                                 @Nullable ColumnHint columnHint) {
        super(command, new String[]{Integer.toString(dbIndex)}, columnHint, null, false);
        this.dbIndex = dbIndex;
    }

    public int getDbIndex() {
        return dbIndex;
    }
}
