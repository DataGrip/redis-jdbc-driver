package jdbc.properties;

import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisCluster;

public class RedisDefaultConfig implements JedisClientConfig {

    public static final RedisDefaultConfig CONFIG = new RedisDefaultConfig();

    private RedisDefaultConfig() {
    }

    public int getMaxAttempts() {
        return JedisCluster.DEFAULT_MAX_ATTEMPTS;
    }

    public boolean isVerifyServerCertificate() {
        return true;
    }
}
