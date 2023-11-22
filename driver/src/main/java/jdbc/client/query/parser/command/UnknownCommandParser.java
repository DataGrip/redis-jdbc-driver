package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

public class UnknownCommandParser extends CommandParser<ProtocolCommand> {

    UnknownCommandParser() {
        super();
    }

    @Override
    protected @Nullable ProtocolCommand parseCommand(@NotNull String commandName) {
        return null;
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
