package jdbc.client;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

import java.util.*;
import java.util.function.Function;

public class RedisJedisClusterURI implements JedisClientConfig {

    private static final String PREFIX = "jdbc:redis:cluster://";

    public static boolean acceptsURL(String url) {
        return url != null && url.startsWith(PREFIX);
    }


    private Set<HostAndPort> nodes;

    private String user;
    private String password;
    private int database;
    private int maxAttempts;


    public RedisJedisClusterURI(String url, Properties info) {
        if (!acceptsURL(url))
            throw new IllegalArgumentException(String.format("Incorrect URL: URL needs to start with %s", PREFIX));

        String uri = url.replaceFirst(PREFIX, "");

        String authBlock = "";
        int atIndex = uri.indexOf('@');
        if (atIndex >= 0) {
            authBlock = uri.substring(0, atIndex);
            uri = uri.substring(atIndex + 1);
        }
        setAuth(authBlock, info);

        String nodesBlock;
        int slashIndex = uri.indexOf('/');
        int questionIndex = uri.indexOf('?');
        if (slashIndex >= 0) {
            nodesBlock = uri.substring(0, slashIndex);
            uri = uri.substring(slashIndex);
        } else if (questionIndex >= 0) {
            nodesBlock = uri.substring(0, questionIndex);
            uri = uri.substring(questionIndex);
        } else {
            nodesBlock = uri;
            uri = "";
        }
        setNodes(nodesBlock);

        String databaseBlock = "";
        if (slashIndex >= 0) {
            uri = uri.replaceFirst("/", "");
            if (questionIndex >= 0) {
                databaseBlock = uri.substring(0, questionIndex);
                uri = uri.substring(questionIndex);
            } else {
                databaseBlock = uri;
                uri = "";
            }
        }
        setDatabase(databaseBlock, info);

        String parametersBlock = "";
        if (questionIndex >= 0) {
            uri = uri.replaceFirst("\\?", "");
            parametersBlock = uri;
        }
        setParameters(parametersBlock, info);
    }


    private void setAuth(String authBlock, Properties info) {
        String user = JedisClientConfig.super.getUser();
        String password = JedisClientConfig.super.getPassword();

        if (!authBlock.isEmpty()) {
            String[] authParts = authBlock.split(":", 2);
            if (authParts.length == 1) {
                password = authParts[0];
            } else {
                user = authParts[0];
                password = authParts[1];
            }
        }

        this.user = getStringOption(info, "user", user);
        this.password = getStringOption(info, "password", password);
    }

    private void setNodes(String nodesBlock) {
        Set<HostAndPort> nodes = new HashSet<>();

        String[] nodesParts = nodesBlock.split(",");
        for (String nodeBlock : nodesParts) {
            String host = Protocol.DEFAULT_HOST;
            int port = Protocol.DEFAULT_PORT;

            if (!nodeBlock.isEmpty()) {
                String[] nodeParts = nodeBlock.split(":", 2);
                if (nodeParts.length == 1) {
                    host = nodeParts[0];
                } else {
                    host = nodeParts[0];
                    port = Integer.parseInt(nodeParts[1]);
                }
            }

            nodes.add(new HostAndPort(host, port));
        }

        this.nodes = nodes;
    }

    private void setDatabase(String databaseBlock, Properties info) {
        int database = JedisClientConfig.super.getDatabase();

        if (!databaseBlock.isEmpty()) {
            database = Integer.parseInt(databaseBlock);
        }

        this.database = getIntOption(info, "database", database);
    }

    private void setParameters(String parametersBlock, Properties info) {
        Map<String, String> parameters = new HashMap<>();
        String[] parametersParts = parametersBlock.split(",");
        for (String parameterBlock : parametersParts) {
            if (!parameterBlock.isEmpty()) {
                String[] nodeParts = parameterBlock.split("=", 2);
                if (nodeParts.length == 2) {
                    parameters.putIfAbsent(nodeParts[0], nodeParts[1]);
                } 
            }
        }

        int maxAttempts = getIntOption(parameters, "maxAttempts", JedisCluster.DEFAULT_MAX_ATTEMPTS);
        
        this.maxAttempts = getIntOption(info, "maxAttempts", maxAttempts);
    }


    private <T> T getOption(Map<?, ?> info, String optionName, T defaultValue, Function<String, T> valueGetter) {
        if (info != null) {
            Object option = info.get(optionName);
            if (option != null) return valueGetter.apply(option.toString());
        }
        return defaultValue;
    }

    private String getStringOption(Map<?, ?> info, String optionName, String defaultValue) {
        return getOption(info, optionName, defaultValue, Object::toString);
    }

    private int getIntOption(Map<?, ?> info, String optionName, int defaultValue) {
        return getOption(info, optionName, defaultValue, Integer::parseInt);
    }


    public Set<HostAndPort> getNodes() {
        return nodes;
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
        return database;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
