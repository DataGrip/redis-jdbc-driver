package jdbc.client.result;

import jdbc.client.query.structures.RedisQuery;
import jdbc.client.result.parser.ResultParser;
import jdbc.client.result.parser.ResultParsers;
import jdbc.client.result.structures.RedisResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisResultHelper {

    private RedisResultHelper() {
    }

    public static @NotNull RedisResult parseResult(@Nullable Object data, @NotNull RedisQuery query) {
        ResultParser resultParser = ResultParsers.get(query);
        if (resultParser != null) {
            try {
                return resultParser.parse(data, query);
            }
            catch (Exception ignored) {
            }
        }
        return ResultParsers.getDefault().parse(data, query);
    }
}
