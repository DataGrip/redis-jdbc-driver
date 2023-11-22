package jdbc.client.query.structures;

import jdbc.client.commands.RedisCommand;
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
                      @NotNull Params params,
                      @Nullable ColumnHint columnHint,
                      @Nullable NodeHint nodeHint,
                      boolean isBlocking) {
        this.command = command;
        this.params = params;
        this.columnHint = columnHint;
        this.nodeHint = nodeHint;
        this.isBlocking = isBlocking;
    }

    public @Nullable String getSampleKey() {
        return null;
    }

    public @NotNull RedisCommand getCommand() {
        return command;
    }

    public @NotNull ProtocolCommand getRawCommand() {
        return command.getRawCommand();
    }

    public @NotNull Params getParams() {
        return params;
    }

    public @NotNull String[] getRawParams() {
        return params.getRawParams();
    }

    public @Nullable ColumnHint getColumnHint() {
        return columnHint;
    }

    public @Nullable NodeHint getNodeHint() {
        return nodeHint;
    }

    public boolean isBlocking() {
        return isBlocking;
    }
}
