package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedisListResult extends RedisResultBase<SimpleType<?>, List<Object>> {

    private final boolean raw;

    public RedisListResult(@NotNull SimpleType<?> type,
                           @NotNull List<Object> result,
                           @NotNull RedisQuery query,
                           boolean raw) {
        super(type, result, query);
        this.raw = raw;
    }

    public boolean isRaw() {
        return raw;
    }
}