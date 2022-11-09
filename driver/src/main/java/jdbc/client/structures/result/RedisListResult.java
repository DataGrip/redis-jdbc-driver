package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedisListResult extends RedisResultBase {

    private final String type;
    private final List<Object> result;

    public RedisListResult(@NotNull String type, @NotNull List<Object> result, @Nullable ColumnHint columnHint) {
        super(columnHint);
        this.type = type;
        this.result = result;
    }

    @Override
    public @NotNull String getType() {
        return type;
    }

    @Override
    public @NotNull List<Object> getResult() {
        return result;
    }
}