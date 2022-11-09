package jdbc.client.helpers.result.parser;

import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ResultParser {
    @NotNull RedisResult parse(@NotNull RedisQuery query, @Nullable Object data);
}
