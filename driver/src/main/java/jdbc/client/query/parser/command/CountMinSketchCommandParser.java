package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.bloom.RedisBloomProtocol.CountMinSketchCommand;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;

import static jdbc.utils.Utils.toNameMap;

class CountMinSketchCommandParser extends CommandParser {

    private static final Map<String, CountMinSketchCommand> COUNT_MIN_SKETCH_COMMANDS = toNameMap(CountMinSketchCommand.values());

    @Override
    protected @Nullable CountMinSketchCommand parseCommand(@NotNull String commandName) {
        return COUNT_MIN_SKETCH_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
