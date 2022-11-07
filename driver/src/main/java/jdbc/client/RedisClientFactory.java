package jdbc.client;

import java.sql.SQLException;
import java.util.Properties;

public class RedisClientFactory {

    private RedisClientFactory() {
    }

    public static boolean acceptsURL(String url) {
        return RedisJedisURI.acceptsURL(url);
    }

    public static RedisClient create(String url, Properties info) throws SQLException {
        if (RedisJedisURI.acceptsURL(url)) return new RedisJedisClient(url, info);
        return null;
    }
}
