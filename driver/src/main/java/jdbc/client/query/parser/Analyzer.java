package jdbc.client.query.parser;

import jdbc.client.commands.RedisCommand;
import jdbc.client.commands.RedisCommands;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Analyzer {

    private Analyzer() {
    }

    private static final Set<RedisCommand> BLOCKING_COMMANDS = Set.of(
            RedisCommands.BLMOVE, RedisCommands.BLMPOP, RedisCommands.BLPOP, RedisCommands.BRPOP,
            RedisCommands.BRPOPLPUSH, RedisCommands.BZMPOP, RedisCommands.BZPOPMAX, RedisCommands.BZPOPMIN
    );

    static boolean isBlocking(@NotNull RedisCommand command) {
        return BLOCKING_COMMANDS.contains(command);
    }
}
