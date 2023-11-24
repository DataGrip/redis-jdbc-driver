package jdbc.client.result.parser;

import jdbc.client.query.structures.RedisQuery;
import jdbc.client.result.structures.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResultParser {
    @NotNull RedisResult parse(@Nullable Object data, @NotNull RedisQuery query);
}
