package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.bloom.RedisBloomProtocol.TDigestCommand;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;

import static jdbc.utils.Utils.toNameMap;

class TDigestCommandParser extends CommandParser {

    private static final Map<String, TDigestCommand> T_DIGEST_COMMANDS = toNameMap(TDigestCommand.values());

    @Override
    protected @Nullable TDigestCommand parseCommand(@NotNull String commandName) {
        return T_DIGEST_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
