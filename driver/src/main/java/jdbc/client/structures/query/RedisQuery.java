package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final String[] params;
    private Set<String> paramsSet;

    private final ColumnHint columnHint;
    private final NodeHint nodeHint;

    private final boolean isBlocking;

    public RedisQuery(@NotNull CompositeCommand compositeCommand,
                      @NotNull String[] params,
                      @Nullable ColumnHint columnHint,
                      @Nullable NodeHint nodeHint,
                      boolean isBlocking) {
        this.compositeCommand = compositeCommand;
        this.params = params;
        this.columnHint = columnHint;
        this.nodeHint = nodeHint;
        this.isBlocking = isBlocking;
    }

    @NotNull
    public CompositeCommand getCompositeCommand() {
        return compositeCommand;
    }

    @NotNull
    public ProtocolCommand getCommand() {
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
    public NodeHint getNodeHint() {
        return nodeHint;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    @Nullable
    public String getSampleKey() {
        return null;
    }

    public boolean containsParam(@NotNull Keyword paramKeyword) {
        if (paramsSet == null) {
            paramsSet = Arrays.stream(params).map(Utils::toUpperCase).collect(Collectors.toSet());
        }
        return paramsSet.contains(paramKeyword.name());
    }
}
