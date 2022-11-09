package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final ColumnHint columnHint;

    public RedisQuery(@NotNull CompositeCommand compositeCommand, @Nullable ColumnHint columnHint) {
        this.compositeCommand = compositeCommand;
        this.columnHint = columnHint;
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
        return compositeCommand.getParams();
    }

    public ColumnHint getColumnHint() {
        return columnHint;
    }
}
