package jdbc.client;

import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisClientConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

abstract class RedisJedisURIBase implements JedisClientConfig {

    // auth
    private String user;
    private String password;

    // database
    private int database;

    // parameters
    private int connectionTimeout;
    private int socketTimeout;
    private int blockingSocketTimeout;
    private String clientName;

    public RedisJedisURIBase(String url, Properties info) {
        String uri = extractURI(url);

        String authBlock = "";
        int atIndex = uri.indexOf('@');
        if (atIndex >= 0) {
            authBlock = uri.substring(0, atIndex);
            uri = uri.substring(atIndex + 1);
        }
        setAuth(authBlock, info);

        String hostAndPortBlock;
        int slashIndex = uri.indexOf('/');
        int questionIndex = uri.indexOf('?');
        if (slashIndex >= 0) {
            hostAndPortBlock = uri.substring(0, slashIndex);
            uri = uri.substring(slashIndex);
        } else if (questionIndex >= 0) {
            hostAndPortBlock = uri.substring(0, questionIndex);
            uri = uri.substring(questionIndex);
        } else {
            hostAndPortBlock = uri;
            uri = "";
        }
        setHostAndPort(hostAndPortBlock);

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


    private @NotNull String extractURI(String url) {
        if (url == null)
            throw new IllegalArgumentException("Empty URL");
        String prefix = getPrefix();
        if (!url.startsWith(prefix))
            throw new IllegalArgumentException(String.format("Incorrect URL: URL needs to start with %s", prefix));
        return url.replaceFirst(prefix, "");
    }

    protected abstract @NotNull String getPrefix();


    private void setAuth(@NotNull String authBlock, Properties info) {
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

    protected abstract void setHostAndPort(@NotNull String hostAndPortBlock);

    private void setDatabase(@NotNull String databaseBlock, Properties info) {
        int database = JedisClientConfig.super.getDatabase();

        if (!databaseBlock.isEmpty()) {
            database = Integer.parseInt(databaseBlock);
        }

        this.database = getIntOption(info, "database", database);
    }

    private void setParameters(@NotNull String parametersBlock, Properties info) {
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
        setParameters(parameters, info);
    }

    protected void setParameters(@NotNull Map<String, String> parameters, Properties info) {
        this.connectionTimeout =
                getIntOption(parameters, info, "connectionTimeout", JedisClientConfig.super.getConnectionTimeoutMillis());
        this.socketTimeout =
                getIntOption(parameters, info, "socketTimeout", JedisClientConfig.super.getSocketTimeoutMillis());
        this.blockingSocketTimeout =
                getIntOption(parameters, info, "blockingSocketTimeout", JedisClientConfig.super.getBlockingSocketTimeoutMillis());
        this.clientName =
                getStringOption(parameters, info, "clientName", JedisClientConfig.super.getClientName());
    }


    protected String getStringOption(Map<String, String> parameters, Properties info, String optionName, String defaultValue) {
        String parameter = getStringOption(parameters, optionName, defaultValue);
        return getStringOption(info, optionName, parameter);
    }

    protected int getIntOption(Map<String, String> parameters, Properties info, String optionName, int defaultValue) {
        int parameter = getIntOption(parameters, optionName, defaultValue);
        return getIntOption(info, optionName, parameter);
    }

    protected String getStringOption(Map<?, ?> map, String optionName, String defaultValue) {
        return getOption(map, optionName, defaultValue, Object::toString);
    }

    protected int getIntOption(Map<?, ?> map, String optionName, int defaultValue) {
        return getOption(map, optionName, defaultValue, Integer::parseInt);
    }

    private <T> T getOption(Map<?, ?> map, String optionName, T defaultValue, Function<String, T> valueGetter) {
        if (map != null) {
            Object option = map.get(optionName);
            if (option != null) return valueGetter.apply(option.toString());
        }
        return defaultValue;
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

    @Override
    public int getConnectionTimeoutMillis() {
        return connectionTimeout;
    }

    @Override
    public int getSocketTimeoutMillis() {
        return socketTimeout;
    }

    @Override
    public int getBlockingSocketTimeoutMillis() {
        return blockingSocketTimeout;
    }

    @Override
    public String getClientName() {
        return clientName;
    }
}

