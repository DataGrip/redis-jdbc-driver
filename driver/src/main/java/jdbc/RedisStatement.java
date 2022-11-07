package jdbc;

import jdbc.client.Client;
import jdbc.client.structures.result.RedisListResult;
import jdbc.client.structures.result.RedisMapResult;
import jdbc.client.structures.result.RedisObjectResult;
import jdbc.client.structures.result.RedisResult;
import jdbc.resultset.RedisEmptyResultSet;
import jdbc.resultset.RedisListResultSet;
import jdbc.resultset.RedisMapResultSet;
import jdbc.resultset.RedisObjectResultSet;

import java.sql.*;

public class RedisStatement implements Statement {

    private final RedisConnection connection;
    private final Client client;

    private boolean isClosed = false;

    private ResultSet resultSet;

    RedisStatement(RedisConnection connection, Client client) {
        this.connection = connection;
        this.client = client;
    }

    private ResultSet createResultSet(RedisResult result) {
        if (result instanceof RedisListResult) return new RedisListResultSet(this, (RedisListResult) result);
        if (result instanceof RedisMapResult) return new RedisMapResultSet(this, (RedisMapResult) result);
        if (result instanceof RedisObjectResult) return new RedisObjectResultSet(this, (RedisObjectResult) result);
        return new RedisEmptyResultSet(this);
    }

    private ResultSet executeImpl(String sql) throws SQLException {
        RedisResult result = client.execute(sql);
        return createResultSet(result);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        checkClosed();
        return executeImpl(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        checkClosed();
        executeImpl(sql);
        return 1;
    }

    @Override
    public void close() throws SQLException {
        isClosed = true;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        // TODO (implement later) ?
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void cancel() throws SQLException {
        // TODO (implement later) ?
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        // TODO (implement later) ?
        checkClosed();
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        // TODO (implement later) ?
        checkClosed();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        // TODO (implement later) ?
        checkClosed();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        checkClosed();
        this.resultSet = executeImpl(sql);
        return true;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        checkClosed();
        return resultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        // TODO (implement later) ?
        checkClosed();
        return -1;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        checkClosed();
        return getMoreResults(CLOSE_CURRENT_RESULT);
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        // TODO (implement later) ?
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        checkClosed();
        // TODO (implement) ?
    }

    @Override
    public void clearBatch() throws SQLException {
        checkClosed();
        // TODO (implement) ?
    }

    @Override
    public int[] executeBatch() throws SQLException {
        checkClosed();
        // TODO (implement) ?
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        checkClosed();
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    private void checkClosed() throws SQLException {
        if (isClosed) throw new SQLException("Statement was previously closed.");
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
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
