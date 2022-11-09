package jdbc.client.helpers.query.parser;

import jdbc.client.helpers.query.parser.lexer.Lexer;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.query.RedisSetDatabaseQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class QueryParser {

    private QueryParser() {
    }


    private static final Map<String, Protocol.Command> COMMANDS =
            Arrays.stream(Protocol.Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Protocol.Keyword> KEYWORDS =
            Arrays.stream(Protocol.Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Protocol.Command> COMMANDS_WITH_PREFIX_KEYWORDS = Set.of(
            Protocol.Command.ACL, Protocol.Command.CLIENT, Protocol.Command.CLUSTER, Protocol.Command.CONFIG,
            Protocol.Command.MEMORY, Protocol.Command.MODULE, Protocol.Command.OBJECT, Protocol.Command.PUBSUB,
            Protocol.Command.SCRIPT, Protocol.Command.SLOWLOG, Protocol.Command.XGROUP, Protocol.Command.XINFO
    );

    private static final Set<Protocol.Command> COMMANDS_WITH_POSTFIX_KEYWORDS = Set.of(
            Protocol.Command.ZDIFF, Protocol.Command.ZINTER, Protocol.Command.ZRANDMEMBER, Protocol.Command.ZRANGE, Protocol.Command.ZRANGEBYSCORE,
            Protocol.Command.ZREVRANGE, Protocol.Command.ZREVRANGEBYSCORE, Protocol.Command.HRANDFIELD
    );

    private static @Nullable Protocol.Command getCommand(@NotNull String command) {
        return COMMANDS.get(command.toUpperCase(Locale.ENGLISH));
    }

    private static @Nullable Protocol.Keyword getKeyword(@NotNull String keyword) {
        return KEYWORDS.get(keyword.toUpperCase(Locale.ENGLISH));
    }


    public static @NotNull RedisQuery parse(@Nullable String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        List<String> tokens = Lexer.tokenize(sql);
        if (tokens.isEmpty()) throw new SQLException("Empty query.");
        Protocol.Command redisCommand = parseCommand(tokens.get(0));
        String[] params = tokens.stream().skip(1).toArray(String[]::new);
        Protocol.Keyword redisKeyword = parseKeyword(redisCommand, params);
        return createQuery(redisCommand, redisKeyword, params);
    }

    private static @NotNull Protocol.Command parseCommand(@NotNull String command) throws SQLException {
        Protocol.Command redisCommand = getCommand(command);
        if (redisCommand == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", command));
        return redisCommand;
    }

    private static @Nullable Protocol.Keyword parseKeyword(@NotNull Protocol.Command redisCommand,
                                                           @NotNull String[] params) throws SQLException {
        if (COMMANDS_WITH_PREFIX_KEYWORDS.contains(redisCommand)) {
            return parsePrefixKeyword(redisCommand, params.length > 0 ? params[0] : null);
        }
        if (COMMANDS_WITH_POSTFIX_KEYWORDS.contains(redisCommand)) {
            return parseSuffixKeyword(redisCommand, params.length > 0 ? params[params.length - 1] : null);
        }
        return null;
    }

    private static @NotNull Protocol.Keyword parsePrefixKeyword(@NotNull Protocol.Command redisCommand,
                                                                @Nullable String keyword) throws SQLException {
        if (keyword == null)
            throw new SQLException(String.format("Query does not contain a keyword for the command %s", redisCommand));
        Protocol.Keyword redisKeyword = getKeyword(keyword);
        if (redisKeyword == null)
            throw new SQLException(String.format(
                    "Query contains an unknown keyword for the command %s: %s",
                    redisCommand,
                    keyword
            ));
        return redisKeyword;
    }

    private static @Nullable Protocol.Keyword parseSuffixKeyword(@NotNull Protocol.Command redisCommand, @Nullable String keyword) {
        return keyword == null ? null : getKeyword(keyword);
    }


    private static @NotNull RedisQuery createQuery(@NotNull Protocol.Command redisCommand,
                                                   @Nullable Protocol.Keyword redisKeyword,
                                                   @NotNull String[] params) throws SQLException {
        if (redisCommand == Protocol.Command.SELECT) {
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
