package jdbc.client.impl;

import jdbc.utils.SSLUtils.SSLParamsException;
import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.HostAndPortMapper;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static jdbc.properties.RedisDefaultConfig.CONFIG;
import static jdbc.properties.RedisDriverPropertyInfoHelper.*;
import static jdbc.utils.SSLUtils.getTrustEverybodySSLContext;
import static jdbc.utils.Utils.*;

public abstract class RedisJedisURIBase implements JedisClientConfig {

    // auth
    private String user;
    private String password;

    // database
    private int database;

    // common parameters
    private int connectionTimeout;
    private int socketTimeout;
    private int blockingSocketTimeout;
    private String clientName;

    // ssl parameters
    private boolean ssl;
    private SSLSocketFactory sslSocketFactory;

    // host and port mapper
    private CompleteHostAndPortMapper hostAndPortMapper;


    protected RedisJedisURIBase(String url, Properties info) throws SQLException {
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
            uri = uri.substring(slashIndex + 1);
        } else if (questionIndex >= 0) {
            hostAndPortBlock = uri.substring(0, questionIndex);
            uri = uri.substring(questionIndex + 1);
        } else {
            hostAndPortBlock = uri;
            uri = "";
        }
        setHostAndPort(hostAndPortBlock);

        String databaseBlock = "";
        if (slashIndex >= 0) {
            questionIndex = uri.indexOf('?');
            if (questionIndex >= 0) {
                databaseBlock = uri.substring(0, questionIndex);
                uri = uri.substring(questionIndex + 1);
            } else {
                databaseBlock = uri;
                uri = "";
            }
        }
        setDatabase(databaseBlock, info);

        String parametersBlock = "";
        if (questionIndex >= 0) {
            parametersBlock = uri;
        }
        setParameters(parametersBlock, info);

        setHostAndPortMapping(info);
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
        String user = CONFIG.getUser();
        String password = CONFIG.getPassword();

        if (!authBlock.isEmpty()) {
            String[] authParts = authBlock.split(":", 2);
            if (authParts.length == 1) {
                password = authParts[0];
            } else {
                user = authParts[0];
                password = authParts[1];
            }
        }

        this.user = Utils.getString(info, USER, user);
        this.password = Utils.getString(info, PASSWORD, password);
    }


    protected abstract void setHostAndPort(@NotNull String hostAndPortBlock);


    private void setDatabase(@NotNull String databaseBlock, Properties info) {
        int database = CONFIG.getDatabase();

        if (!databaseBlock.isEmpty()) {
            database = parseDbIndex(databaseBlock);
        }

        this.database = Utils.getInt(info, DATABASE, database);
    }


    private void setParameters(@NotNull String parametersBlock, Properties info) throws SQLException {
        Map<String, String> parameters = new HashMap<>();
        String[] parametersParts = parametersBlock.split("&");
        for (String parameterBlock : parametersParts) {
            if (!parameterBlock.isEmpty()) {
                String[] nodeParts = parameterBlock.split("=", 2);
                if (nodeParts.length == 2) {
                    parameters.putIfAbsent(nodeParts[0], nodeParts[1]);
                }
            }
        }
        setCommonParameters(parameters, info);
        setSSLParameters(parameters, info);
    }

    protected void setCommonParameters(@NotNull Map<String, String> parameters, Properties info) {
        connectionTimeout = getInt(parameters, info, CONNECTION_TIMEOUT, CONFIG.getConnectionTimeoutMillis());
        socketTimeout = getInt(parameters, info, SOCKET_TIMEOUT, CONFIG.getSocketTimeoutMillis());
        blockingSocketTimeout = getInt(parameters, info, BLOCKING_SOCKET_TIMEOUT, CONFIG.getBlockingSocketTimeoutMillis());
        clientName = getString(parameters, info, CLIENT_NAME, CONFIG.getClientName());
    }

    private void setSSLParameters(@NotNull Map<String, String> parameters, Properties info) throws SSLParamsException {
        ssl = getBoolean(parameters, info, SSL, CONFIG.isSsl());
        if (ssl) {
            boolean verifyServerCertificate = getBoolean(parameters, info, VERIFY_SERVER_CERTIFICATE, CONFIG.isVerifyServerCertificate());
            if (!verifyServerCertificate) {
                String keyStoreType = System.getProperty("javax.net.ssl.keyStoreType", KeyStore.getDefaultType());
                String keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword", "");
                String keyStoreUrl = System.getProperty("javax.net.ssl.keyStore", "");
                // check keyStoreUrl
                if (!isNullOrEmpty(keyStoreUrl)) {
                    try {
                        new URL(keyStoreUrl);
                    } catch (MalformedURLException e) {
                        keyStoreUrl = "file:" + keyStoreUrl;
                    }
                }
                SSLContext context = getTrustEverybodySSLContext(keyStoreUrl, keyStoreType, keyStorePassword);
                sslSocketFactory = context.getSocketFactory();
            }
        }
    }

    protected String getString(Map<String, String> parameters, Properties info, String name, String defaultValue) {
        String parameter = Utils.getString(parameters, name, defaultValue);
        return Utils.getString(info, name, parameter);
    }

    protected int getInt(Map<String, String> parameters, Properties info, String name, int defaultValue) {
        int parameter = Utils.getInt(parameters, name, defaultValue);
        return Utils.getInt(info, name, parameter);
    }

    protected boolean getBoolean(Map<String, String> parameters, Properties info, String name, boolean defaultValue) {
        boolean parameter = Utils.getBoolean(parameters, name, defaultValue);
        return Utils.getBoolean(info, name, parameter);
    }


    private void setHostAndPortMapping(Properties info) {
        Map<HostAndPort, HostAndPort> mapping = getMap(info, HOST_AND_PORT_MAPPING, Utils::parseHostAndPort, Utils::parseHostAndPort);
        this.hostAndPortMapper = mapping == null ? null : new CompleteHostAndPortMapper(mapping);
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

    @Override
    public boolean isSsl() {
        return ssl;
    }

    @Override
    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    @Override
    public CompleteHostAndPortMapper getHostAndPortMapper() {
        return hostAndPortMapper;
    }


    public static class CompleteHostAndPortMapper implements HostAndPortMapper {
        private final Map<HostAndPort, HostAndPort> mapping;

        public CompleteHostAndPortMapper(@NotNull Map<HostAndPort, HostAndPort> mapping) {
            this.mapping = mapping;
        }

        @Override
        public HostAndPort getHostAndPort(HostAndPort hap) throws JedisConnectionException {
            if (hap == null) return null;
            HostAndPort mappedHap = mapping.get(hap);
            if (mappedHap == null) {
                if (mapping.containsValue(hap)) return hap;
                throw new JedisConnectionException(String.format("Port forwarding is not specified for %s", hap));
            }
            return mappedHap;
        }
    }
}

