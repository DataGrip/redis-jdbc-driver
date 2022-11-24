package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisBlockingQuery extends RedisQuery {
    public RedisBlockingQuery(@NotNull CompositeCommand compositeCommand,
                              @NotNull String[] params,
                              @Nullable ColumnHint columnHint) {
        super(compositeCommand, params, columnHint);
    }
}
