package jdbc.client.structures.result;

import jdbc.client.structures.query.ColumnHint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.util.Locale;

abstract class RedisResultBase<T, R> implements RedisResult {

    private final String command;
    private final T type;
    private final R result;
    private final ColumnHint columnHint;

    protected RedisResultBase(@NotNull Protocol.Command command,
                              @NotNull T type,
                              @NotNull R result,
                              @Nullable ColumnHint columnHint) {
        this.command = command.name().toLowerCase(Locale.ENGLISH);
        this.type = type;
        this.result = result;
        this.columnHint = columnHint;
    }

    public @NotNull String getCommand() {
        return command;
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
