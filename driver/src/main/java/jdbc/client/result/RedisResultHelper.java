package jdbc.client.result;

import jdbc.client.query.structures.RedisQuery;
import jdbc.client.result.parser.ResultParsers;
import jdbc.client.result.structures.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    public static @NotNull RedisResult parseResult(@NotNull RedisQuery query, @Nullable Object data) {
        return ResultParsers.get(query).parse(query, data);
    }
}
