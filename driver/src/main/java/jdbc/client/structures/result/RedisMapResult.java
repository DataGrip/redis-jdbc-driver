package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RedisMapResult extends RedisResultBase {

    private final String type;
    private final Map<String, Object> result;

    public RedisMapResult(@NotNull String type, @NotNull Map<String, Object> result, @Nullable ColumnHint columnHint) {
        super(columnHint);
        this.type = type;
        this.result = result;
    }

    @Override
    public @NotNull String getType() {
        return type;
    }

    @Override
    public @NotNull Map<String, Object> getResult() {
        return result;
    }
}
