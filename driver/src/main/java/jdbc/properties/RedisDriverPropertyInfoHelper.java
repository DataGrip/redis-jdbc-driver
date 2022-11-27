package jdbc.properties;

import java.sql.DriverPropertyInfo;
import java.util.ArrayList;

import static jdbc.properties.RedisDefaultConfig.CONFIG;

public class RedisDriverPropertyInfoHelper {
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String DATABASE = "database";
    public static final String CONNECTION_TIMEOUT = "connectionTimeout";
    public static final String SOCKET_TIMEOUT = "socketTimeout";
    public static final String BLOCKING_SOCKET_TIMEOUT = "blockingSocketTimeout";
    public static final String CLIENT_NAME = "clientName";
    public static final String MAX_ATTEMPTS = "maxAttempts";

    private RedisDriverPropertyInfoHelper() {
    }

    public static DriverPropertyInfo[] getPropertyInfo() {
        ArrayList<DriverPropertyInfo> propInfos = new ArrayList<>();
        addPropInfo(propInfos, USER, CONFIG.getUser(), "ACL user.");
        addPropInfo(propInfos, PASSWORD, CONFIG.getPassword(), "Password.");
        addPropInfo(propInfos, DATABASE, String.valueOf(CONFIG.getDatabase()), "Database.");
        addPropInfo(propInfos, CONNECTION_TIMEOUT, String.valueOf(CONFIG.getConnectionTimeoutMillis()), "Connection timeout in milliseconds.");
        addPropInfo(propInfos, SOCKET_TIMEOUT, String.valueOf(CONFIG.getSocketTimeoutMillis()), "Socket timeout in milliseconds.");
        addPropInfo(propInfos, BLOCKING_SOCKET_TIMEOUT, String.valueOf(CONFIG.getBlockingSocketTimeoutMillis()), "Socket timeout (in milliseconds) to use during blocking operation. Default is '0', which means to block forever.");
        addPropInfo(propInfos, CLIENT_NAME, CONFIG.getClientName(), "Client name.");
        // TODO: support Redis Cluster
        // addPropInfo(propInfos, MAX_ATTEMPTS, String.valueOf(CONFIG.getMaxAttempts()), "Maximum number of attempts (cluster only).");
        return propInfos.toArray(new DriverPropertyInfo[0]);
    }

    private static void addPropInfo(final ArrayList<DriverPropertyInfo> propInfos, final String propName,
                                    final String defaultVal, final String description) {
        DriverPropertyInfo newProp = new DriverPropertyInfo(propName, defaultVal);
        newProp.description = description;
        propInfos.add(newProp);
    }
}
