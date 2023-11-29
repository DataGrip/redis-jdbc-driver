package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

abstract class CommandOrExParser extends CommandParser {

    @Override
    protected final @Nullable ProtocolCommand parseCommand(@NotNull String commandName) {
        ProtocolCommand commandEx = parseCommandEx(commandName);
        if (commandEx != null) return commandEx;
        return parseCommandOr(commandName);
    }

    protected abstract @Nullable ProtocolCommand parseCommandOr(@NotNull String commandName);

    protected abstract @Nullable ProtocolCommand parseCommandEx(@NotNull String commandName);
}
