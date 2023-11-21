package jdbc.client.helpers.query.parser;

import jdbc.client.structures.RedisCommand;
import jdbc.client.structures.RedisCommands;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class QueryAnalyzer {

    private QueryAnalyzer() {
    }

    private static final Set<RedisCommand> BLOCKING_COMMANDS = Set.of(
            RedisCommands.BLMOVE, RedisCommands.BLMPOP, RedisCommands.BLPOP, RedisCommands.BRPOP,
            RedisCommands.BRPOPLPUSH, RedisCommands.BZMPOP, RedisCommands.BZPOPMAX, RedisCommands.BZPOPMIN
    );

    public static boolean isBlocking(@NotNull RedisCommand command) {
        return BLOCKING_COMMANDS.contains(command);
    }
}
