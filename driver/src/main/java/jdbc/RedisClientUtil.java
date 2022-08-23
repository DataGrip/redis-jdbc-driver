package jdbc;

import redis.clients.jedis.Protocol;
import redis.clients.jedis.util.SafeEncoder;

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

    public static List<?> encodeResult(Object result) {
        if (result == null) return Collections.emptyList();
        Object encodedResult = SafeEncoder.encodeObject(result);
        if (encodedResult instanceof List) return (List<?>) encodedResult;
        return Collections.singletonList(encodedResult);
    }
}
