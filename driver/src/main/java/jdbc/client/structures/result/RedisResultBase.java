package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class RedisResultBase<T, R> implements RedisResult {

    private final T type;
    private final R result;
    private final ColumnHint columnHint;

    protected RedisResultBase(@NotNull T type, @NotNull R result, @Nullable ColumnHint columnHint) {
        this.type = type;
        this.result = result;
        this.columnHint = columnHint;
    }

    public @NotNull T getType() {
        return type;
    }

    public @NotNull R getResult() {
        return result;
    }

    @Override
    public @Nullable ColumnHint getColumnHint() {
        return columnHint;
    }
}
