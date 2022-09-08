package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final String[] params;

    public RedisQuery(@NotNull Protocol.Command command, @Nullable Protocol.Keyword keyword, @NotNull String[] params) {
        this.compositeCommand = new CompositeCommand(command, keyword);
        this.params = params;
    }

    @NotNull
    public CompositeCommand getCompositeCommand() {
        return compositeCommand;
    }

    @NotNull
    public Protocol.Command getCommand() {
        return compositeCommand.getCommand();
    }

    @NotNull
    public String[] getParams() {
        return params;
    }
}
