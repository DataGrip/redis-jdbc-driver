package jdbc.client;

import jdbc.client.impl.cluster.RedisJedisClusterURI;
import jdbc.client.impl.standalone.RedisJedisClient;
import jdbc.client.impl.standalone.RedisJedisURI;

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
        // TODO: support Redis Cluster
        // if (RedisJedisClusterURI.acceptsURL(url)) return new RedisJedisClusterClient(new RedisJedisClusterURI(url, info));
        if (RedisJedisClusterURI.acceptsURL(url)) throw  new SQLException("Redis Cluster is not supported.");
        return null;
    }
}
