package jdbc.client.helpers.query.parser;

import jdbc.client.structures.RedisCommand;
import jdbc.client.structures.query.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

import java.sql.SQLException;

import static jdbc.utils.Utils.getName;

abstract class CommandParser<T extends ProtocolCommand> {

    private final String commandName;
    private final Params params;

    CommandParser(@NotNull String commandName, @NotNull Params params) {
        this.commandName = commandName;
        this.params = params;
    }

    public @NotNull RedisCommand parse() throws SQLException {
        T command = parseCommand(commandName);
        if (command != null && hasKeyword(command)) {
            String keywordName = getKeywordName(params);
            if (keywordName == null)
                throw new SQLException(String.format("Query does not contain a keyword for the command %s.", command));
            return new RedisCommand(command, commandName, keywordName);
        }
        return new RedisCommand(command, commandName, null);
    }

    protected abstract @Nullable T parseCommand(@NotNull String commandName);

    protected abstract boolean hasKeyword(@NotNull T command);

    @Nullable
    private static String getKeywordName(@NotNull Params params) {
        String firstParam = params.getFirst();
        return firstParam != null ? getName(firstParam) : null;
    }
}
