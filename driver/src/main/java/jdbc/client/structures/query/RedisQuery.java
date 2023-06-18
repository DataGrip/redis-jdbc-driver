package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final String[] params;
    private Set<String> paramsSet;

    private final ColumnHint columnHint;

    private final Integer forcedSlot;
    private final boolean isBlocking;

    public RedisQuery(@NotNull CompositeCommand compositeCommand,
                      @NotNull String[] params,
                      @Nullable ColumnHint columnHint,
                      @Nullable Integer forcedSlot,
                      boolean isBlocking) {
        this.compositeCommand = compositeCommand;
        this.params = params;
        this.columnHint = columnHint;
        this.forcedSlot = forcedSlot;
        this.isBlocking = isBlocking;
    }

    @NotNull
    public CompositeCommand getCompositeCommand() {
        return compositeCommand;
    }

    @NotNull
    public Command getCommand() {
        return compositeCommand.getCommand();
    }

    @NotNull
    public String[] getParams() {
        return params;
    }

    @Nullable
    public ColumnHint getColumnHint() {
        return columnHint;
    }

    @Nullable
    public Integer getForcedSlot() {
        return forcedSlot;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean containsParam(@NotNull Keyword paramKeyword) {
        if (paramsSet == null) {
            paramsSet = Arrays.stream(params).map(Utils::toUpperCase).collect(Collectors.toSet());
        }
        return paramsSet.contains(paramKeyword.name());
    }
}
