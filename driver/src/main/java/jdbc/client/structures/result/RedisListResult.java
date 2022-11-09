package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedisListResult extends RedisResultBase<String, List<Object>> {
    public RedisListResult(@NotNull String type, @NotNull List<Object> result, @Nullable ColumnHint columnHint) {
        super(type, result, columnHint);
    }
}