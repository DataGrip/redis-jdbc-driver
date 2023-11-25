package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

abstract class CommandParserWithoutEx<T extends ProtocolCommand> extends CommandParser<T, ProtocolCommand> {

    @Override
    protected @Nullable ProtocolCommand parseCommandEx(@NotNull String commandName) {
        return null;
    }
}
