package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class RedisObjectResult extends RedisResultBase {

    private final Map<String, String> type;
    private final List<Map<String, Object>> result;

    public RedisObjectResult(@NotNull Map<String, String> type,
                             @NotNull List<Map<String, Object>> result,
                             @Nullable ColumnHint columnHint) {
        super(columnHint);
        this.type = type;
        this.result = result;
    }

    @Override
    public @NotNull Map<String, String> getType() {
        return type;
    }

    @Override
    public @NotNull List<Map<String, Object>> getResult() {
        return result;
    }
}

