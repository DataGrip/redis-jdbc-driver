package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RedisMapResult extends RedisResultBase<SimpleType<?>, Map<String, Object>> {
    public RedisMapResult(@NotNull RedisQuery query,
                          @NotNull SimpleType<?> type,
                          @NotNull Map<String, Object> result) {
        super(query, type, result);
    }
}
