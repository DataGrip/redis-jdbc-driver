package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedisListResult extends RedisResultBase<SimpleType<?>, List<Object>> {
    public RedisListResult(@NotNull RedisQuery query,
                           @NotNull SimpleType<?> type,
                           @NotNull List<Object> result) {
        super(query, type, result);
    }
}