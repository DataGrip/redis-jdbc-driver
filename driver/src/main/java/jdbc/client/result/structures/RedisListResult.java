package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedisListResult extends RedisResultBase<SimpleType<?>, List<Object>> {

    private final boolean raw;

    public RedisListResult(@NotNull RedisQuery query,
                           @NotNull SimpleType<?> type,
                           @NotNull List<Object> result,
                           boolean raw) {
        super(query, type, result);
        this.raw = raw;
    }

    public boolean isRaw() {
        return raw;
    }
}