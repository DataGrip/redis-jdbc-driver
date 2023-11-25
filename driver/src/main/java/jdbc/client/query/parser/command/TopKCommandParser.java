package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.bloom.RedisBloomProtocol.TopKCommand;
import redis.clients.jedis.commands.ProtocolCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

public class TopKCommandParser extends CommandParserWithoutEx<TopKCommand> {

    private static final Map<String, TopKCommand> TOP_K_COMMANDS = toMap(TopKCommand.values());

    TopKCommandParser() {
        super();
    }

    @Override
    protected @Nullable TopKCommand parseCommand(@NotNull String commandName) {
        return TOP_K_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return false;
    }
}
