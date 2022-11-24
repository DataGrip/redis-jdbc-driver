package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisBlockingQuery extends RedisQuery {
    public RedisBlockingQuery(@NotNull CompositeCommand compositeCommand, @Nullable ColumnHint columnHint) {
        super(compositeCommand, columnHint);
    }
}
