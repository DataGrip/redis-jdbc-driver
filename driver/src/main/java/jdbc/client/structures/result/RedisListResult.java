package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedisListResult extends RedisResultBase<SimpleType<?>, List<Object>> {
    public RedisListResult(@NotNull RedisQuery query,
                           @NotNull SimpleType<?> type,
                           @NotNull List<Object> result) {
        super(query, type, result);
    }
}