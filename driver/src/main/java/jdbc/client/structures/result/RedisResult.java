package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;

public interface RedisResult<T, R> {
    @NotNull RedisQuery getQuery();
    @NotNull T getType();
    @NotNull R getResult();
}
