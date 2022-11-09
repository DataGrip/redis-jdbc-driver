package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;

abstract class RedisResultBase<T, R> implements RedisResult<T, R> {

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

    @Override
    public @NotNull RedisQuery getQuery() {
        return query;
    }

    @Override
    public @NotNull T getType() {
        return type;
    }

    @Override
    public @NotNull R getResult() {
        return result;
    }
}
