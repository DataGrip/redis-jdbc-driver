package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.util.Map;

public class RedisMapResult extends RedisResultBase<String, Map<String, Object>> {
    public RedisMapResult(@NotNull Protocol.Command command,
                          @NotNull String type,
                          @NotNull Map<String, Object> result,
                          @Nullable ColumnHint columnHint) {
        super(command, type, result, columnHint);
    }
}
