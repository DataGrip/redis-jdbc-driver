package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

public class RedisQuery {

    private final CompositeCommand compositeCommand;
    private final String[] params;

    public RedisQuery(@NotNull Command command, @Nullable Keyword keyword, @NotNull String[] params) {
        this.compositeCommand = new CompositeCommand(command, keyword);
        this.params = params;
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
}
