package jdbc.client.structures.result;

import org.jetbrains.annotations.NotNull;

public interface RedisResult {
    @NotNull
    Object getType();

    @NotNull
    Object getResult();
}
