package jdbc;

import jdbc.client.RedisClient;
import jdbc.client.RedisClientFactory;
import jdbc.properties.RedisDriverPropertyInfoHelper;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

import static jdbc.properties.RedisDriverPropertyInfoHelper.VERIFY_CONNECTION_MODE;
import static jdbc.properties.RedisDriverPropertyInfoHelper.VERIFY_CONNECTION_MODE_DEFAULT;
import static jdbc.utils.Utils.getBoolean;

public class RedisDriver implements Driver {

    static {
        try {
            DriverManager.registerDriver(new RedisDriver());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        RedisClient client = RedisClientFactory.create(url, info);
        if (client == null) return null;
        RedisConnection connection = new RedisConnection(this, client);
        try {
            checkConnectionModeIfNeeded(info, connection);
        } catch (SQLException e) {
            connection.close();
            throw e;
        }
        return connection;
    }

    private static void checkConnectionModeIfNeeded(Properties info, @NotNull RedisConnection connection) throws SQLException {
        if (!getBoolean(info, VERIFY_CONNECTION_MODE, VERIFY_CONNECTION_MODE_DEFAULT)) return;
        connection.checkConnectionMode();
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return RedisClientFactory.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return RedisDriverPropertyInfoHelper.getPropertyInfo();
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 4;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
