package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedisKeysPatternQuery extends RedisQuery {

    private final String keysPattern;

    public RedisKeysPatternQuery(@NotNull CompositeCommand compositeCommand,
                                 @NotNull String[] params,
                                 @Nullable String keysPattern,
                                 @Nullable ColumnHint columnHint,
                                 @Nullable NodeHint nodeHint,
                                 boolean isBlocking) {
        super(compositeCommand, params, columnHint, nodeHint, isBlocking);
        this.keysPattern = keysPattern;
    }

    @Nullable
    public String getKeysPattern() {
        return keysPattern;
    }

    @Nullable
    @Override
    public String getSampleKey() {
        return keysPattern;
    }
}
