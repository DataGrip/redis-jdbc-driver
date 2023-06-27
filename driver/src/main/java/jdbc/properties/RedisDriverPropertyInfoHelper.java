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
    public static final String SSL = "ssl";
    public static final String VERIFY_SERVER_CERTIFICATE = "verifyServerCertificate";

    public static final String HOST_AND_PORT_MAPPING = "hostAndPortMapping";
    public static final String HOST_AND_PORT_MAPPING_DEFAULT = null;

    public static final String VERIFY_CONNECTION_MODE = "verifyConnectionMode";
    public static final boolean VERIFY_CONNECTION_MODE_DEFAULT = true;

    private static final String[] booleanChoices = new String[]{Boolean.TRUE.toString(), Boolean.FALSE.toString()};


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
        addPropInfo(propInfos, MAX_ATTEMPTS, String.valueOf(CONFIG.getMaxAttempts()), "Maximum number of attempts (cluster only).");
        addPropInfo(propInfos, SSL, String.valueOf(CONFIG.isSsl()), "Enable SSL.", booleanChoices);
        addPropInfo(propInfos, VERIFY_SERVER_CERTIFICATE, String.valueOf(CONFIG.isVerifyServerCertificate()),
                "Configure a connection that uses SSL but does not verify the identity of the server.", booleanChoices);
        addPropInfo(propInfos, HOST_AND_PORT_MAPPING, HOST_AND_PORT_MAPPING_DEFAULT, "Host and port mapping.");
        addPropInfo(propInfos, VERIFY_CONNECTION_MODE, String.valueOf(VERIFY_CONNECTION_MODE_DEFAULT),
                "Verify that mode specified for a connection in the URL scheme matches the server mode.", booleanChoices);
        return propInfos.toArray(new DriverPropertyInfo[0]);
    }

    private static void addPropInfo(final ArrayList<DriverPropertyInfo> propInfos, final String propName,
                                    final String defaultVal, final String description) {
        addPropInfo(propInfos, propName, defaultVal, description, null);
    }

    private static void addPropInfo(final ArrayList<DriverPropertyInfo> propInfos, final String propName,
                                    final String defaultVal, final String description, final String[] choices) {
        DriverPropertyInfo newProp = new DriverPropertyInfo(propName, defaultVal);
        newProp.description = description;
        if (choices != null) {
            newProp.choices = choices;
        }
        propInfos.add(newProp);
    }
}
