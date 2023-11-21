package jdbc.client.helpers.query.parser;

import jdbc.client.structures.query.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

public class JsonCommandParser extends CommandParser<JsonCommand> {

    private static final String JSON_COMMAND_PREFIX = "JSON.";
    private static final Map<String, JsonCommand> JSON_COMMANDS = toMap(JsonCommand.values());

    JsonCommandParser(@NotNull String commandName, @NotNull Params params) {
        super(commandName, params);
    }

    @Override
    protected @Nullable JsonCommand parseCommand(@NotNull String commandName) {
        return JSON_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull JsonCommand command) {
        return command == JsonCommand.DEBUG;
    }


    public static boolean accepts(@NotNull String commandName) {
        return commandName.startsWith(JSON_COMMAND_PREFIX);
    }
}
