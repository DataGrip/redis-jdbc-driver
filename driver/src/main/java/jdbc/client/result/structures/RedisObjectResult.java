package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class RedisObjectResult extends RedisResultBase<ObjectType<?>, List<Map<String, Object>>> {
    public RedisObjectResult(@NotNull ObjectType<?> type,
                             @NotNull List<Map<String, Object>> result,
                             @NotNull RedisQuery query) {
        super(type, result, query);
    }
}

