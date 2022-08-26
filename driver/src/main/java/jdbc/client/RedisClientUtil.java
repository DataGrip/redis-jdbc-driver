package jdbc.client;

import jdbc.client.RedisQuery.CompositeCommand;
import jdbc.resultset.RedisEmptyResultSet;
import jdbc.resultset.RedisListResultSet;
import jdbc.resultset.RedisMapResultSet;
import jdbc.resultset.RedisObjectResultSet;
import redis.clients.jedis.Builder;
import redis.clients.jedis.BuilderFactory;
import redis.clients.jedis.Protocol;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RedisClientUtil {

    private RedisClientUtil() {
    }


    private static final Map<String, Protocol.Command> COMMANDS =
            Arrays.stream(Protocol.Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Protocol.Keyword> KEYWORDS =
            Arrays.stream(Protocol.Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Protocol.Command> COMMANDS_WITH_KEYWORDS = Set.of(
            Protocol.Command.ACL, Protocol.Command.CLIENT, Protocol.Command.CLUSTER, Protocol.Command.CONFIG,
            Protocol.Command.MEMORY, Protocol.Command.MODULE, Protocol.Command.OBJECT, Protocol.Command.PUBSUB,
            Protocol.Command.SCRIPT, Protocol.Command.SLOWLOG, Protocol.Command.XGROUP, Protocol.Command.XINFO
    );

    private static Protocol.Command parseCommand(String command) throws SQLException {
        Protocol.Command redisCommand = COMMANDS.get(command.toUpperCase(Locale.ENGLISH));
        if (redisCommand == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", command));
        return redisCommand;
    }

    private static Protocol.Keyword parseKeyword(Protocol.Command redisCommand, String keyword) {
        if (!COMMANDS_WITH_KEYWORDS.contains(redisCommand)) return null;
        return KEYWORDS.get(keyword.toUpperCase(Locale.ENGLISH));
    }

    public static RedisQuery parseQuery(String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        String[] tokens = sql.split("\\s+");
        if (tokens.length == 0) throw new SQLException("Empty query.");
        Protocol.Command redisCommand = parseCommand(tokens[0]);
        Protocol.Keyword redisKeyword = tokens.length > 1 ?  parseKeyword(redisCommand ,tokens[1]) : null;
        String[] params = Arrays.stream(tokens).skip(1).toArray(String[]::new);
        return new RedisQuery(redisCommand, redisKeyword, params);
    }


    private static final Map<CompositeCommand, Builder<?>> RESULT_BUILDERS = new HashMap<>() {{
        put(CompositeCommand.create(Protocol.Command.HGETALL), BuilderFactory.STRING_MAP);
        put(CompositeCommand.create(Protocol.Command.CONFIG, Protocol.Keyword.GET), BuilderFactory.STRING_MAP);
    }};

    private static Builder<?> getResultBuilder(CompositeCommand command) {
        return RESULT_BUILDERS.getOrDefault(command, BuilderFactory.ENCODED_OBJECT);
    }

    private static Object encodeResult(RedisQuery query, Object result) {
        if (result == null) return null;
        Builder<?> resultBuilder = getResultBuilder(query.getCompositeCommand());
        return resultBuilder.build(result);
    }

    public static ResultSet createResultSet(RedisQuery query, Object result) {
        Object encodedResult = encodeResult(query, result);
        if (encodedResult == null) return new RedisEmptyResultSet();
        if (encodedResult instanceof Map) return new RedisMapResultSet((Map<?, ?>) encodedResult);
        if (encodedResult instanceof List) return new RedisListResultSet((List<?>) encodedResult);
        return new RedisObjectResultSet(encodedResult);
    }
}
