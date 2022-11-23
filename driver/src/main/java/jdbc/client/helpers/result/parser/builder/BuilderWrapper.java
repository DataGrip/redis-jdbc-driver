package jdbc.client.helpers.result.parser.builder;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Builder;

public abstract class BuilderWrapper<T> extends Builder<T> {
    public abstract @NotNull T build(Object data);
}
