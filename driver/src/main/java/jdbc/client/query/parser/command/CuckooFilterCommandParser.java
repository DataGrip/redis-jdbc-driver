package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.bloom.RedisBloomProtocol.CuckooFilterCommand;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;

import static jdbc.utils.Utils.toNameMap;

class CuckooFilterCommandParser extends CommandParser {

    private static final Map<String, CuckooFilterCommand> CUCKOO_FILTER_COMMANDS = toNameMap(CuckooFilterCommand.values());

    @Override
    protected @Nullable CuckooFilterCommand parseCommand(@NotNull String commandName) {
        return CUCKOO_FILTER_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}

