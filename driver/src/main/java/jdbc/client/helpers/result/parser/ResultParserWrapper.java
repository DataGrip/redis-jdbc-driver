package jdbc.client.helpers.result.parser;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ResultParserWrapper {
    private final ResultParser resultParser;
    private final Predicate<RedisQuery> isApplicable;

    private ResultParserWrapper(@NotNull ResultParser resultParser,
                                @Nullable Predicate<RedisQuery> isApplicable) {
        this.resultParser = resultParser;
        this.isApplicable = isApplicable;
    }

    public @NotNull ResultParser getResultParser() {
        return resultParser;
    }

    public boolean isApplicable(@NotNull RedisQuery query) {
        return isApplicable == null || isApplicable.test(query);
    }

    public static ResultParserWrapper wrap(@NotNull ResultParser resultParser,
                                           @Nullable Predicate<RedisQuery> isApplicable) {
        return new ResultParserWrapper(resultParser, isApplicable);
    }

    public static ResultParserWrapper wrap(@NotNull ResultParser resultParser) {
        return wrap(resultParser, null);
    }

    public static List<ResultParserWrapper> wrapList(@NotNull ResultParser resultParser,
                                                     @Nullable Predicate<RedisQuery> isApplicable) {
        return Collections.singletonList(wrap(resultParser, isApplicable));
    }

    public static List<ResultParserWrapper> wrapList(@NotNull ResultParser resultParser) {
        return wrapList(resultParser, null);
    }
}
