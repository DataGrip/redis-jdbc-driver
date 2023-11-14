package jdbc.client.helpers.query.parser;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

class CommandParser {

    private CommandParser() {
    }


    private static final Map<String, Command> COMMANDS =
            Arrays.stream(Command.values()).collect(Collectors.toMap(Enum::name, v -> v));


    // TODO (stack): think about nullable
    public static @Nullable ProtocolCommand parseCommand(@NotNull String command) {
        String commandName = Utils.getName(command);
        ProtocolCommand knownCommand = parseKnownCommand(commandName);
        if (knownCommand != null) return knownCommand;
        return new UnknownCommand(commandName);
    }

    private static @Nullable ProtocolCommand parseKnownCommand(@NotNull String commandName) {
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
