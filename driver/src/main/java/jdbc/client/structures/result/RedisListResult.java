package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.util.List;

public class RedisListResult extends RedisResultBase<String, List<Object>> {
    public RedisListResult(@NotNull Protocol.Command command,
                           @NotNull String type,
                           @NotNull List<Object> result,
                           @Nullable ColumnHint columnHint) {
        super(command, type, result, columnHint);
    }
}