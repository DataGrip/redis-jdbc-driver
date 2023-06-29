package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisKeyPatternQuery extends RedisQuery {

    private final String keyPattern;

    public RedisKeyPatternQuery(@NotNull CompositeCommand compositeCommand,
                                @NotNull String[] params,
                                @Nullable String keyPattern,
                                @Nullable ColumnHint columnHint,
                                @Nullable NodeHint nodeHint,
                                boolean isBlocking) {
        super(compositeCommand, params, columnHint, nodeHint, isBlocking);
        this.keyPattern = keyPattern;
    }

    @Nullable
    public String getKeyPattern() {
        return keyPattern;
    }

    @Nullable
    @Override
    public String getSampleKey() {
        return keyPattern;
    }
}
