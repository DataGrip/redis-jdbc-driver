package jdbc.client.query.parser;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;
import java.util.Set;

import static jdbc.utils.Utils.toMap;

public class NativeCommandParser extends CommandParser<Command> {

    private static final Map<String, Command> COMMANDS = toMap(Command.values());
    private static final Set<ProtocolCommand> COMMANDS_WITH_KEYWORDS = Set.of(
            Command.ACL, Command.CLIENT, Command.CLUSTER, Command.SENTINEL, Command.COMMAND,
            Command.CONFIG, Command.FUNCTION, Command.MEMORY, Command.MODULE, Command.OBJECT,
            Command.PUBSUB, Command.SCRIPT, Command.SLOWLOG, Command.XGROUP, Command.XINFO
    );

    NativeCommandParser(@NotNull String commandName, @NotNull Params params) {
        super(commandName, params);
    }

    @Override
    protected @Nullable Command parseCommand(@NotNull String commandName) {
        return COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull Command command) {
        return COMMANDS_WITH_KEYWORDS.contains(command);
    }


    public static boolean accepts(@NotNull String commandName) {
        return !commandName.contains(".");
    }
}
