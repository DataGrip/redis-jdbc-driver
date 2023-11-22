package jdbc.utils;

import jdbc.client.commands.RedisCommand;
import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.args.Rawable;
import redis.clients.jedis.util.SafeEncoder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {
    }


    public static boolean isNullOrEmpty(@Nullable String value) {
        return value == null || value.isEmpty();
    }


    public static @NotNull String toLowerCase(@NotNull String value) {
        return value.toLowerCase(Locale.ENGLISH);
    }

    public static @NotNull String toUpperCase(@NotNull String value) {
        return value.toUpperCase(Locale.ENGLISH);
    }

    public static @NotNull String toCapitalized(@NotNull String value) {
        return value.isEmpty() ? value : toUpperCase(value.substring(0, 1)) + toLowerCase(value.substring(1));
    }


    public static String getString(Map<?, ?> map, String name, String defaultValue) {
        return getObject(map, name, defaultValue, Object::toString);
    }

    public static int getInt(Map<?, ?> map, String name, int defaultValue) {
        return getObject(map, name, defaultValue, Integer::parseInt);
    }

    public static boolean getBoolean(Map<?, ?> map, String name, boolean defaultValue) {
        return getObject(map, name, defaultValue, Boolean::parseBoolean);
    }

    public static <K, V> Map<K, V> getMap(Map<?, ?> map, String name,
                                          @NotNull Function<String, K> keyParser,
                                          @NotNull Function<String, V> valueParser) {
        return getObject(map, name, null, o -> parseMap(keyParser, valueParser, o));
    }

    @NotNull
    private static <K, V> Map<K, V> parseMap(@NotNull Function<String, K> keyParser,
                                             @NotNull Function<String, V> valueParser,
                                             @NotNull String map) {
        Map<K, V> result = new HashMap<>();
        String content = map.trim();
        if (!content.startsWith("{") || !content.endsWith("}"))
            throw new IllegalArgumentException(String.format("Incorrect map: %s", map));
        String body = content.substring(1, content.length() - 1).trim();
        if (body.isEmpty()) return result;
        String[] elements = body.split(",");
        for (String element : elements) {
            element = element.trim();
            String[] keyValue = element.split("=");
            if (keyValue.length != 2)
                throw new IllegalArgumentException(String.format("Incorrect map: %s", map));
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            result.put(keyParser.apply(key), valueParser.apply(value));
        }
        return result;
    }

    private static <T> T getObject(Map<?, ?> map, String name, T defaultValue, Function<String, T> valueParser) {
        if (map != null) {
            Object option = map.get(name);
            if (option != null) return valueParser.apply(option.toString());
        }
        return defaultValue;
    }


    public static @NotNull HostAndPort parseHostAndPort(@NotNull String hostAndPortStr) {
        HostAndPort hostAndPort = HostAndPort.from(hostAndPortStr);
        String host = hostAndPort.getHost();
        String adjustedHost = adjustHost(host);
        if (adjustedHost.equals(host)) return hostAndPort;
        return new HostAndPort(adjustedHost, hostAndPort.getPort());
    }

    public static @NotNull String adjustHost(@NotNull String host) {
        String adjustedHost = host.trim();
        if ("localhost".equalsIgnoreCase(adjustedHost)) {
            return Protocol.DEFAULT_HOST;
        }
        return adjustedHost;
    }


    public static int parseSqlDbIndex(@Nullable String db) throws SQLException {
        if (db == null) throw new SQLException("Database should be specified.");
        try {
            return parseDbIndex(db);
        } catch (IllegalArgumentException e) {
            throw new SQLException(e);
        }
    }

    public static int parseDbIndex(@NotNull String db) {
        try {
            return Integer.parseInt(db);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Database should be a number: %s.", db));
        }
    }


    public static @NotNull Predicate<Params> contains(@NotNull Keyword keyword) {
        return params -> params.contains(keyword);
    }

    public static @NotNull Predicate<Params> length(int length) {
        return params -> params.getLength() == length;
    }


    public static @NotNull String getColumnTitle(@NotNull RedisCommand command) {
        String lowerName = toLowerCase(command.toString());
        if (lowerName.equals("zscore") || lowerName.equals("zmscore")) return "score"; // for consistency with ObjectType<Tuple>
        return lowerName;
    }


    @Contract("null -> null; !null -> !null")
    public static @Nullable String getName(@Nullable Rawable rawable) {
        if (rawable == null) return null;
        byte[] raw = rawable.getRaw();
        String text = raw == null ? "" : SafeEncoder.encode(raw);
        return getName(text);
    }

    public static @NotNull String getName(@NotNull String text) {
        return toUpperCase(text);
    }


    public static <T extends Enum<?>> @NotNull Map<String, T> toMap(T @NotNull [] values) {
        return Arrays.stream(values).collect(Collectors.toMap(Enum::name, v -> v));
    }
}
