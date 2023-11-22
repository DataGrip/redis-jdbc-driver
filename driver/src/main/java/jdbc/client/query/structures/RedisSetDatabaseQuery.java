package jdbc.client.query.structures;

import jdbc.client.commands.RedisCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisSetDatabaseQuery extends RedisQuery {

    private final int dbIndex;

    public RedisSetDatabaseQuery(@NotNull RedisCommand command,
                                 @NotNull Params params,
                                 int dbIndex,
                                 @Nullable ColumnHint columnHint,
                                 @Nullable NodeHint nodeHint,
                                 boolean isBlocking) {
        super(command, params, columnHint, nodeHint, isBlocking);
        this.dbIndex = dbIndex;
    }

    public int getDbIndex() {
        return dbIndex;
    }
}
