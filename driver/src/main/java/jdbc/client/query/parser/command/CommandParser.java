package jdbc.client.query.parser.command;

import jdbc.client.commands.RedisCommand;
import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;

public interface CommandParser {
    @NotNull RedisCommand parse(@NotNull String commandName, @NotNull Params params);
}
