package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

abstract class CommandParserWithoutExBase<T extends ProtocolCommand> extends CommandParserBase<T, ProtocolCommand> {
    @Override
    protected @Nullable ProtocolCommand parseCommandEx(@NotNull String commandName) {
        return null;
    }
}
