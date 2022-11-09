package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;

public abstract class RedisResultBase<T, R> implements RedisResult {

    private final RedisQuery query;
    private final T type;
    private final R result;

    protected RedisResultBase(@NotNull RedisQuery query,
                              @NotNull T type,
                              @NotNull R result) {
        this.query = query;
        this.type = type;
        this.result = result;
    }

    public @NotNull RedisQuery getQuery() {
        return query;
    }

    public @NotNull T getType() {
        return type;
    }

    public @NotNull R getResult() {
        return result;
    }
}
