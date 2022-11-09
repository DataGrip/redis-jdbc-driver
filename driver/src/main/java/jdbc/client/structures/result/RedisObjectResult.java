package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.util.List;
import java.util.Map;

public class RedisObjectResult extends RedisResultBase<Map<String, String>, List<Map<String, Object>>> {
    public RedisObjectResult(@NotNull Protocol.Command command,
                             @NotNull Map<String, String> type,
                             @NotNull List<Map<String, Object>> result,
                             @Nullable ColumnHint columnHint) {
        super(command, type, result, columnHint);
    }
}

