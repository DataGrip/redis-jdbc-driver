package jdbc.client.query.parser.command;

import jdbc.client.commands.RedisCommand;
import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

import static jdbc.utils.Utils.getName;

public abstract class CommandParser {

    public final @NotNull RedisCommand parse(@NotNull String commandName, @NotNull Params params) {
        ProtocolCommand command = parseCommand(commandName);
        if (command != null && hasKeyword(command)) {
            return new RedisCommand(command, commandName, getKeywordName(params));
        }
        return new RedisCommand(command, commandName, null);
    }

    protected abstract @Nullable ProtocolCommand parseCommand(@NotNull String commandName);

    protected abstract boolean hasKeyword(@NotNull ProtocolCommand command);

    @Nullable
    private static String getKeywordName(@NotNull Params params) {
        String firstParam = params.getFirst();
        return firstParam != null ? getName(firstParam) : null;
    }
}
