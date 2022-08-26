package jdbc.client;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class RedisURI implements JedisClientConfig {

    private static final String PREFIX = "jdbc:redis://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }

    private final HostAndPort hostAndPort;

    private final String user;
    private final String password;
    private final Integer databaseIndex;

    public RedisURI(String url, Properties info) {
        try {
            if (!acceptsURL(url))
                throw new IllegalArgumentException(String.format("Incorrect URL: URL needs to start with %s", PREFIX));

            URI uri = new URI(url.replaceFirst("jdbc:", ""));

            String host = uri.getHost();
            int port = uri.getPort();
            hostAndPort = new HostAndPort(
                    host == null ? Protocol.DEFAULT_HOST : host,
                    port == 0 ? Protocol.DEFAULT_PORT : port
            );

            String userInfo = uri.getUserInfo();
            String[] userInfoParts = userInfo == null ? new String[0] : userInfo.split(":", 2);
            user = getOption(info, "user", userInfoParts.length > 0 ? userInfoParts[0] : JedisClientConfig.super.getUser());
            password = getOption(info, "password", userInfoParts.length > 1 ? userInfoParts[1] : JedisClientConfig.super.getPassword());

            String path = uri.getPath();
            String database = path == null ? null : path.replaceFirst("/", "");
            int databaseIndex = JedisClientConfig.super.getDatabase();
            try {
                databaseIndex = database == null ? databaseIndex : Integer.parseInt(database);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(String.format("Incorrect URL: database should be a number: %s", database), e);
            }
            this.databaseIndex = databaseIndex;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Incorrect URL", e);
        }
    }

    private String getOption(Properties info, String optionName, String defaultValue) {
        if (info != null) {
            String option = (String) info.get(optionName);
            if (option != null) {
                return option;
            }
        }
        return defaultValue;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getDatabase() {
        return databaseIndex;
    }
}
