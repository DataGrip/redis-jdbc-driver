package jdbc.client.structures.query;

import jdbc.client.structures.RedisCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisKeyPatternQuery extends RedisQuery {

    private final String keyPattern;

    public RedisKeyPatternQuery(@NotNull RedisCommand command,
                                @NotNull Params params,
                                @Nullable String keyPattern,
                                @Nullable ColumnHint columnHint,
                                @Nullable NodeHint nodeHint,
                                boolean isBlocking) {
        super(command, params, columnHint, nodeHint, isBlocking);
        this.keyPattern = keyPattern;
    }

    public @Nullable String getKeyPattern() {
        return keyPattern;
    }

    @Override
    public @Nullable String getSampleKey() {
        return keyPattern;
    }
}
