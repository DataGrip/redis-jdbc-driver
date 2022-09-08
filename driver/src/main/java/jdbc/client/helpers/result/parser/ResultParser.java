package jdbc.client.helpers.result.parser;

import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResultParser {
    @NotNull RedisResult parse(@Nullable Object data);
}
