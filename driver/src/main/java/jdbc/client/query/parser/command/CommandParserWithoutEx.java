package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

public abstract class CommandParserWithoutEx<T extends ProtocolCommand> extends CommandParser<T, ProtocolCommand> {

    CommandParserWithoutEx() {
        super();
    }

    @Override
    protected @Nullable ProtocolCommand parseCommandEx(@NotNull String commandName) {
        return null;
    }
}
