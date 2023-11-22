package jdbc.client.query.parser.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

public class JsonCommandParser extends CommandParser<JsonCommand> {

    private static final Map<String, JsonCommand> JSON_COMMANDS = toMap(JsonCommand.values());

    JsonCommandParser() {
        super();
    }

    @Override
    protected @Nullable JsonCommand parseCommand(@NotNull String commandName) {
        return JSON_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull JsonCommand command) {
        return command == JsonCommand.DEBUG;
    }
}
