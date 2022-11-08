package jdbc.client;

import java.sql.SQLException;
import java.util.Properties;

public class RedisClientFactory {

    private RedisClientFactory() {
    }

    public static boolean acceptsURL(String url) {
        return RedisJedisURI.acceptsURL(url) || RedisJedisClusterURI.acceptsURL(url);
    }

    public static RedisClient create(String url, Properties info) throws SQLException {
        if (RedisJedisURI.acceptsURL(url)) return new RedisJedisClient(new RedisJedisURI(url, info));
        if (RedisJedisClusterURI.acceptsURL(url)) return new RedisJedisClusterClient(new RedisJedisClusterURI(url, info));
        return null;
    }
}
