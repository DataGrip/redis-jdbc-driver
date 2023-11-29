package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;

class UnknownModuleCommandParser extends CommandParserWithoutExBase<ProtocolCommand> {

    @Override
    protected @Nullable ProtocolCommand parseCommand(@NotNull String commandName) {
        return null;
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
