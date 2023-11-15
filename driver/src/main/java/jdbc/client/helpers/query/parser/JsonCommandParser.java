package jdbc.client.helpers.query.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

public class JsonCommandParser extends CommandParser<JsonCommand> {

    private static final Map<String, JsonCommand> JSON_COMMANDS = toMap(JsonCommand.values());

    private static final Map<String, Protocol.Keyword> KEYWORDS = toMap(Protocol.Keyword.values());


    @Override
    protected @Nullable JsonCommand parseCommand(@NotNull String commandName) {
        return JSON_COMMANDS.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull JsonCommand command) {
        return command == JsonCommand.DEBUG;
    }

    @Override
    protected @Nullable Rawable parseKeyword(@NotNull JsonCommand command, @NotNull String keywordName) {
        return KEYWORDS.get(keywordName);
    }


    public static boolean accepts(@NotNull String commandName) {
        return commandName.startsWith("JSON.");
    }
}
