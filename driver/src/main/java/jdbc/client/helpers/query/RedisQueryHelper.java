package jdbc.client.helpers.query;

import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.query.RedisSetDatabaseQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisQueryHelper {

    private RedisQueryHelper() {
    }

    private static final Map<String, Command> COMMANDS =
            Arrays.stream(Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Keyword> KEYWORDS =
            Arrays.stream(Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Command> COMMANDS_WITH_PREFIX_KEYWORDS = Set.of(
            Command.ACL, Command.CLIENT, Command.CLUSTER, Command.CONFIG,
            Command.MEMORY, Command.MODULE, Command.OBJECT, Command.PUBSUB,
            Command.SCRIPT, Command.SLOWLOG, Command.XGROUP, Command.XINFO
    );

    private static final Set<Command> COMMANDS_WITH_POSTFIX_KEYWORDS = Set.of(
            Command.ZDIFF, Command.ZINTER, Command.ZRANDMEMBER, Command.ZRANGE, Command.ZRANGEBYSCORE,
            Command.ZREVRANGE, Command.ZREVRANGEBYSCORE, Command.HRANDFIELD
    );

    private static @Nullable Command getCommand(@NotNull String command) {
        return COMMANDS.get(command.toUpperCase(Locale.ENGLISH));
    }

    private static @Nullable Keyword getKeyword(@NotNull String keyword) {
        return KEYWORDS.get(keyword.toUpperCase(Locale.ENGLISH));
    }

    public static @NotNull RedisQuery parseQuery(@Nullable String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        String[] tokens = sql.trim().split("\\s+");
        if (tokens.length == 0) throw new SQLException("Empty query.");
        Command redisCommand = parseCommand(tokens[0]);
        String[] params = Arrays.stream(tokens).skip(1).toArray(String[]::new);
        Keyword redisKeyword = parseKeyword(redisCommand, params);
        return createQuery(redisCommand, redisKeyword, params);
    }

    private static @NotNull Command parseCommand(@NotNull String command) throws SQLException {
        Command redisCommand = getCommand(command);
        if (redisCommand == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", command));
        return redisCommand;
    }

    private static @Nullable Keyword parseKeyword(@NotNull Command redisCommand,
                                                  @NotNull String[] params) throws SQLException {
        if (COMMANDS_WITH_PREFIX_KEYWORDS.contains(redisCommand)) {
            return parsePrefixKeyword(redisCommand, params.length > 0 ? params[0] : null);
        }
        if (COMMANDS_WITH_POSTFIX_KEYWORDS.contains(redisCommand)) {
            return parseSuffixKeyword(redisCommand, params.length > 0 ? params[params.length - 1] : null);
        }
        return null;
    }

    private static @NotNull Keyword parsePrefixKeyword(@NotNull Command redisCommand,
                                                       @Nullable String keyword) throws SQLException {
        if (keyword == null)
            throw new SQLException(String.format("Query does not contain a keyword for the command %s", redisCommand));
        Keyword redisKeyword = getKeyword(keyword);
        if (redisKeyword == null)
            throw new SQLException(String.format(
                    "Query contains an unknown keyword for the command %s: %s",
                    redisCommand,
                    keyword
            ));
        return redisKeyword;
    }


    private static @Nullable Keyword parseSuffixKeyword(@NotNull Command redisCommand, @Nullable String keyword) {
        return keyword == null ? null : getKeyword(keyword);
    }

    private static @NotNull RedisQuery createQuery(@NotNull Command redisCommand,
                                                   @Nullable Keyword redisKeyword,
                                                   @NotNull String[] params) throws SQLException {
        if (redisCommand == Command.SELECT) {
            String db = params.length > 0 ? params[0] : null;
            Integer dbIndex;
            try {
                dbIndex = db == null ? null : Integer.parseInt(db);
            } catch (NumberFormatException e) {
                throw new SQLException(String.format("Database should be a number: %s.", db));
            }
            if (dbIndex == null) {
                throw new SQLException("Database should be specified");
            }
            return new RedisSetDatabaseQuery(dbIndex);
        }
        return new RedisQuery(redisCommand, redisKeyword, params);
    }
}
