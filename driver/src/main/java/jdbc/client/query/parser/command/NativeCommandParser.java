package jdbc.client.query.parser.command;

import jdbc.client.commands.ProtocolExtensions.CommandEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;
import java.util.Set;

import static jdbc.utils.Utils.toMap;

class NativeCommandParser extends CommandOrExParser {

    private static final Map<String, Command> COMMANDS = toMap(Command.values());
    private static final Map<String, CommandEx> COMMANDS_EX = toMap(CommandEx.values());
    private static final Set<ProtocolCommand> COMMANDS_WITH_KEYWORDS = Set.of(
            Command.ACL, Command.CLIENT, Command.CLUSTER, Command.SENTINEL,
            Command.COMMAND, Command.CONFIG, Command.FUNCTION, Command.LATENCY,
            Command.MEMORY, Command.MODULE, Command.OBJECT, Command.PUBSUB,
            Command.SCRIPT, Command.SLOWLOG, Command.XGROUP, Command.XINFO
    );

    @Override
    protected @Nullable Command parseCommandOr(@NotNull String commandName) {
        return COMMANDS.get(commandName);
    }

    @Override
    protected @Nullable CommandEx parseCommandEx(@NotNull String commandName) {
        return COMMANDS_EX.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return COMMANDS_WITH_KEYWORDS.contains(command);
    }
}
