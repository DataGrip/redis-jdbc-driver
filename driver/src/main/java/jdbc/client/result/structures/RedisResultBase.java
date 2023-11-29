package jdbc.client.result.structures;

import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;

public abstract class RedisResultBase<T, R> implements RedisResult {

    private final T type;
    private final R result;
    private final RedisQuery query;

    protected RedisResultBase(@NotNull T type, @NotNull R result, @NotNull RedisQuery query) {
        this.type = type;
        this.result = result;
        this.query = query;
    }

    public @NotNull T getType() {
        return type;
    }

    public @NotNull R getResult() {
        return result;
    }

    public @NotNull RedisQuery getQuery() {
        return query;
    }
}
