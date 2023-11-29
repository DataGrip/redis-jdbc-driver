package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.bloom.RedisBloomProtocol.BloomFilterCommand;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

class BloomFilterCommandParser extends CommandParser {

    private static final Map<String, BloomFilterCommand> BLOOM_FILTER_COMMANDS = toMap(BloomFilterCommand.values());

    @Override
    protected @Nullable BloomFilterCommand parseCommand(@NotNull String commandName) {
        return BLOOM_FILTER_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
