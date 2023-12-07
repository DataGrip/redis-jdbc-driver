package jdbc;

import jdbc.client.RedisClient;
import jdbc.client.RedisClientFactory;
import jdbc.client.RedisMode;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static jdbc.utils.Utils.toLowerCase;

public class RedisConnection implements Connection {

    private final RedisDriver driver;
    private final RedisClient client;

    private boolean isClosed = false;
    private boolean isReadOnly = false;

    public RedisConnection(RedisDriver driver, RedisClient client) {
        this.driver = driver;
        this.client = client;
    }

    private static final String UNKNOWN_SERVER_MODE_MESSAGE = "Unable to get the server mode" +
            " using the \"INFO server\" command to check if the connection mode matches the server mode.";

    private SQLWarning unknownServerModeWarning = null;

    public void checkMode() throws SQLException {
        RedisMode serverMode;
        try {
            serverMode = getMetaData().getDatabaseProductMode();
        } catch (SQLException e) {
            unknownServerModeWarning = new SQLWarning(UNKNOWN_SERVER_MODE_MESSAGE, e);
            return;
        }
        if (serverMode == null) {
            unknownServerModeWarning = new SQLWarning(UNKNOWN_SERVER_MODE_MESSAGE);
            return;
        }
        RedisMode clientMode = client.getMode();
        if (clientMode != serverMode) {
            String serverURLPrefix = RedisClientFactory.getURLPrefix(serverMode);
            String urlFix = serverURLPrefix != null ? String.format("\nPlease change the URL prefix to \"%s\".", serverURLPrefix) : null;
            throw new SQLException(String.format("The connection mode \"%s\" does not match the server mode \"%s\".%s",
                    toLowerCase(clientMode.name()), toLowerCase(serverMode.name()), urlFix));
        }
    }

    private void checkClosed() throws SQLException {
        if (isClosed) throw new SQLException("Connection was previously closed.");
    }

    @Override
    public Statement createStatement() throws SQLException {
        checkClosed();
        return new RedisStatement(this, client);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        checkClosed();
        return new RedisPreparedStatement(this, client, sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("Redis does not support SQL natively.");
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        // TODO (transactions) ?
        checkClosed();
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        // TODO (transactions) ?
        checkClosed();
        return true;
    }

    @Override
    public void commit() throws SQLException {
        // TODO (transactions) ?
        checkClosed();
    }

    @Override
    public void rollback() throws SQLException {
        // TODO (transactions) ?
        checkClosed();
    }

    @Override
    public void close() throws SQLException {
        if (!isClosed) {
            client.close();
        }
        isClosed = true;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    @Override
    public @NotNull RedisDatabaseMetaData getMetaData() throws SQLException {
        checkClosed();
        return new RedisDatabaseMetaData(this, driver);
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        checkClosed();
        isReadOnly = true;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        checkClosed();
        return isReadOnly;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        checkClosed();
        client.setDatabase(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        checkClosed();
        return client.getDatabase();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        // TODO (transactions) ?
        checkClosed();
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        // TODO (transactions) ?
        checkClosed();
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        checkClosed();
        return unknownServerModeWarning;
    }

    @Override
    public void clearWarnings() throws SQLException {
        checkClosed();
        unknownServerModeWarning = null;
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        // TODO (temporary)
        return createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        // TODO (temporary)
        return prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public NClob createNClob() throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        if (isClosed) return false;
        try (Statement statement = createStatement()) {
            statement.executeQuery("PING");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        /* Redis does not support setting client information in the database. */
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        /* Redis does not support setting client information in the database. */
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        checkClosed();
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        setCatalog(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return getCatalog();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
