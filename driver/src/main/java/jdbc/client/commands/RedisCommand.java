package jdbc.client.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.util.Objects;

public class RedisCommand {

    private final ProtocolCommand command;

    private final String commandName;
    private final String keywordName;

    public RedisCommand(@Nullable ProtocolCommand command, @NotNull String commandName, @Nullable String keywordName) {
        this.command = command != null ? command : new UnknownCommand(commandName);
        this.commandName = commandName;
        this.keywordName = keywordName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RedisCommand)) return false;
        RedisCommand c = (RedisCommand) o;
        return Objects.equals(c.command, command) && Objects.equals(c.keywordName, keywordName);
    }

    @Override
    public int hashCode() {
        return command.hashCode() ^ (keywordName == null ? 0 : keywordName.hashCode());
    }

    @NotNull
    public ProtocolCommand getRawCommand() {
        return command;
    }

    @Override
    public String toString() {
        return keywordName == null ? commandName : String.format("%s %s", commandName, keywordName);
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
