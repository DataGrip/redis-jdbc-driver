package jdbc.client.helpers.result;

import jdbc.client.helpers.result.parser.ResultParsers;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.result.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    public static @NotNull RedisResult parseResult(@NotNull RedisQuery query, @Nullable Object data) {
        return ResultParsers.get(query).parse(query, data);
    }
}
