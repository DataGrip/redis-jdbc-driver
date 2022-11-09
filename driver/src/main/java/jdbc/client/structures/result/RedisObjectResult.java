package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class RedisObjectResult extends RedisResultBase<Map<String, String>, List<Map<String, Object>>> {
    public RedisObjectResult(@NotNull RedisQuery query,
                             @NotNull Map<String, String> type,
                             @NotNull List<Map<String, Object>> result) {
        super(query, type, result);
    }
}

