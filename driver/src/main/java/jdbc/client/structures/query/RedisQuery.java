package jdbc.client.structures.query;

import jdbc.client.structures.RedisCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

public class RedisQuery {

    private final RedisCommand command;
    private final Params params;

    private final ColumnHint columnHint;
    private final NodeHint nodeHint;

    private final boolean isBlocking;

    public RedisQuery(@NotNull RedisCommand command,
                      @NotNull String[] params,
                      @Nullable ColumnHint columnHint,
                      @Nullable NodeHint nodeHint,
                      boolean isBlocking) {
        this.command = command;
        this.params = new Params(params);
        this.columnHint = columnHint;
        this.nodeHint = nodeHint;
        this.isBlocking = isBlocking;
    }

    @NotNull
    public RedisCommand getCommand() {
        return command;
    }

    @NotNull
    public ProtocolCommand getRawCommand() {
        return command.getRawCommand();
    }

    @NotNull
    public Params getParams() {
        return params;
    }

    @NotNull
    public String[] getRawParams() {
        return params.getRawParams();
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
}
