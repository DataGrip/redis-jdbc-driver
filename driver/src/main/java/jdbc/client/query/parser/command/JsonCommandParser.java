package jdbc.client.query.parser.command;

import jdbc.client.commands.ProtocolExtensions.JsonCommandEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;

import java.util.Map;

import static jdbc.utils.Utils.toNameMap;

class JsonCommandParser extends CommandOrExParser {

    private static final Map<String, JsonCommand> JSON_COMMANDS = toNameMap(JsonCommand.values());
    private static final Map<String, JsonCommandEx> JSON_COMMANDS_EX = toNameMap(JsonCommandEx.values());

    @Override
    protected @Nullable JsonCommand parseCommandOr(@NotNull String commandName) {
        return JSON_COMMANDS.get(commandName);
    }

    @Override
    protected @Nullable JsonCommandEx parseCommandEx(@NotNull String commandName) {
        return JSON_COMMANDS_EX.get(commandName);
    }

    @Override
    protected boolean hasKeyword(@NotNull ProtocolCommand command) {
        return command == JsonCommand.DEBUG;
    }
}
