package jdbc;

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

    private static Protocol.Command parseCommand(String command) throws SQLException {
        Protocol.Command redisCommand = COMMANDS.get(command.toUpperCase(Locale.ENGLISH));
        if (redisCommand == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", command));
        return redisCommand;
    }

    public static RedisQuery parseQuery(String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        String[] tokens = sql.split("\\s+");
        if (tokens.length == 0) throw new SQLException("Empty query.");
        Protocol.Command redisCommand = parseCommand(tokens[0]);
        String[] params = Arrays.stream(tokens).skip(1).toArray(String[]::new);
        return new RedisQuery(redisCommand, params);
    }


    private static final Map<Protocol.Command, Builder<?>> RESULT_BUILDERS = new HashMap<>() {{
        put(Protocol.Command.HGETALL, BuilderFactory.STRING_MAP);
    }};

    private static Builder<?> getResultBuilder(Protocol.Command command) {
        return RESULT_BUILDERS.getOrDefault(command, BuilderFactory.ENCODED_OBJECT);
    }

    private static Object encodeResult(RedisQuery query, Object result) {
        if (result == null) return null;
        Builder<?> resultBuilder = getResultBuilder(query.getCommand());
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
