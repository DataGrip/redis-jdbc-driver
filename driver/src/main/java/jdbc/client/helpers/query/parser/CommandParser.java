package jdbc.client.helpers.query.parser;

import jdbc.client.structures.query.CompositeCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

import java.sql.SQLException;

import static jdbc.utils.Utils.getFirst;
import static jdbc.utils.Utils.getName;

abstract class CommandParser<T extends ProtocolCommand> {

    private final String commandName;
    private final String[] params;

    CommandParser(@NotNull String commandName, @NotNull String[] params) {
        this.commandName = commandName;
        this.params = params;
    }

    public @NotNull CompositeCommand parse() throws SQLException {
        T command = parseCommand(commandName);
        if (command != null && hasKeyword(command)) {
            if (!hasKeyword(command)) return CompositeCommand.create(command);
            String keywordName = getKeywordName(params);
            if (keywordName == null)
                throw new SQLException(String.format("Query does not contain a keyword for the command %s.", command));
            return new CompositeCommand(command, commandName, keywordName);
        }
        return new CompositeCommand(command, commandName, null);
    }

    protected abstract @Nullable T parseCommand(@NotNull String commandName);

    protected abstract boolean hasKeyword(@NotNull T command);

    @Nullable
    private static String getKeywordName(@NotNull String[] params) {
        String firstParam = getFirst(params);
        return firstParam != null ? getName(firstParam) : null;
    }
}
