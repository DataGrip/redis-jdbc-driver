package jdbc.client;

import java.sql.SQLException;
import java.util.Properties;

public class RedisClientFactory {

    private RedisClientFactory() {
    }

    public static boolean acceptsURL(String url) {
        return RedisURI.acceptsURL(url);
    }

    public static RedisClient create(String url, Properties info) throws SQLException {
        if (RedisURI.acceptsURL(url)) return new RedisSingleClient(url, info);
        // TODO: (???) return new RedisClusterClient(url, info);
        return null;
    }
}
