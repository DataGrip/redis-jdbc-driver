package jdbc.client;

import jdbc.client.impl.cluster.RedisJedisClusterClient;
import jdbc.client.impl.cluster.RedisJedisClusterURI;
import jdbc.client.impl.standalone.RedisJedisClient;
import jdbc.client.impl.standalone.RedisJedisURI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.Properties;

public class RedisClientFactory {

    private RedisClientFactory() {
    }

    public static boolean acceptsURL(String url) {
        return RedisJedisURI.acceptsURL(url) || RedisJedisClusterURI.acceptsURL(url);
    }

    @Nullable
    public static RedisClient create(String url, Properties info) throws SQLException {
        if (RedisJedisURI.acceptsURL(url)) return new RedisJedisClient(new RedisJedisURI(url, info));
        if (RedisJedisClusterURI.acceptsURL(url)) return new RedisJedisClusterClient(new RedisJedisClusterURI(url, info));
        return null;
    }

    @Nullable
    public static String getURLPrefix(@NotNull RedisMode mode) {
        switch (mode) {
            case STANDALONE:
                return RedisJedisURI.PREFIX;
            case CLUSTER:
                return RedisJedisClusterURI.PREFIX;
            default:
                return null;
        }
    }
}
