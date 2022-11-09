package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class RedisObjectResult extends RedisResultBase<Map<String, String>, List<Map<String, Object>>> {
    public RedisObjectResult(@NotNull Map<String, String> type,
                             @NotNull List<Map<String, Object>> result,
                             @Nullable ColumnHint columnHint) {
        super(type, result, columnHint);
    }
}

