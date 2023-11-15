package jdbc.client.helpers.query.parser;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Map;

import static jdbc.utils.Utils.toMap;

class CommandParser {

    private CommandParser() {
    }


    private static final Map<String, Command> COMMANDS = toMap(Command.values());
    private static final Map<String, JsonCommand> JSON_COMMANDS = toMap(JsonCommand.values());


    public static @NotNull ProtocolCommand parseCommand(@NotNull String command) {
        String commandName = Utils.getName(command);
        ProtocolCommand knownCommand = parseKnownCommand(commandName);
        if (knownCommand != null) return knownCommand;
        return new UnknownCommand(commandName);
    }

    private static @Nullable ProtocolCommand parseKnownCommand(@NotNull String commandName) {
        if (commandName.contains(".")) {
            if (commandName.startsWith("JSON.")) return JSON_COMMANDS.get(commandName);
        }
        return COMMANDS.get(commandName);
    }


    private static class UnknownCommand implements ProtocolCommand {
        private final byte[] raw;

        UnknownCommand(@NotNull String commandName) {
            raw = SafeEncoder.encode(commandName);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }
}
