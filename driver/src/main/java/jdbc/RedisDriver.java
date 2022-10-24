package jdbc;

import jdbc.client.Client;
import jdbc.client.RedisClient;
import jdbc.client.RedisURI;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

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
        if (!acceptsURL(url)) return null;
        Client client = new RedisClient(url, info);
        return new RedisConnection(this, client);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return RedisURI.acceptsURL(url);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return DriverPropertyInfoHelper.getPropertyInfo();
    }

    public String getVersion() {
        return "1.0";
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
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
