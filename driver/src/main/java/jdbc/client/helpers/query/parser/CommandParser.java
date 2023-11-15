package jdbc.client.helpers.query.parser;

import jdbc.client.structures.query.CompositeCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.util.SafeEncoder;

import java.sql.SQLException;

import static jdbc.utils.Utils.getFirst;
import static jdbc.utils.Utils.getName;

abstract class CommandParser<T extends ProtocolCommand> {

    public @NotNull CompositeCommand parseCompositeCommand(@NotNull String commandName,
                                                           @NotNull String[] params) throws SQLException {
        T command = parseCommand(commandName);
        if (command != null && hasKeyword(command)) {
            if (!hasKeyword(command)) return CompositeCommand.create(command);
            String keywordName = getKeywordName(params);
            if (keywordName == null)
                throw new SQLException(String.format("Query does not contain a keyword for the command %s.", command));
            Rawable keyword = parseKeyword(command, keywordName);
            return CompositeCommand.create(command, keyword != null ? keyword : new UnknownKeyword(keywordName));
        }
        return CompositeCommand.create(command != null ? command : new UnknownCommand(commandName));
    }

    protected abstract @Nullable T parseCommand(@NotNull String commandName);

    protected abstract boolean hasKeyword(@NotNull T command);

    @Nullable
    private static String getKeywordName(@NotNull String[] params) {
        String firstParam = getFirst(params);
        return firstParam != null ? getName(firstParam) : null;
    }

    protected abstract @Nullable Rawable parseKeyword(@NotNull T command, @NotNull String keywordName);


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

    private static class UnknownKeyword implements Rawable {
        private final byte[] raw;

        UnknownKeyword(@NotNull String keywordName) {
            raw = SafeEncoder.encode(keywordName);
        }

        @Override
        public byte[] getRaw() {
            return raw;
        }
    }
}
