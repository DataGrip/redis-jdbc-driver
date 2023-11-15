package jdbc.client.helpers.query.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.ClusterKeyword;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.Protocol.SentinelKeyword;
import redis.clients.jedis.args.Rawable;
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

    private static final Map<String, Keyword> KEYWORDS = toMap(Keyword.values());
    private static final Map<String, ClusterKeyword> CLUSTER_KEYWORDS = toMap(ClusterKeyword.values());
    private static final Map<String, SentinelKeyword> SENTINEL_KEYWORDS = toMap(SentinelKeyword.values());


    @Override
    protected @Nullable Command parseCommand(@NotNull String commandName) {
        return COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull Command command) {
        return COMMANDS_WITH_KEYWORDS.contains(command);
    }

    @Override
    protected @Nullable Rawable parseKeyword(@NotNull Command command, @NotNull String keywordName) {
        if (command == Command.CLUSTER) return CLUSTER_KEYWORDS.get(keywordName);
        if (command == Command.SENTINEL) return SENTINEL_KEYWORDS.get(keywordName);
        return KEYWORDS.get(keywordName);
    }
}
