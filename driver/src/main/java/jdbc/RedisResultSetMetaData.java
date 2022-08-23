package jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;

public class RedisResultSetMetaData implements ResultSetMetaData {

    @Override
    public int getColumnCount() throws SQLException {
        // TODO (result set)
        return 1;
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        // TODO (result set)
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int isNullable(int column) throws SQLException {
        return ResultSetMetaData.columnNoNulls;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return "result";
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return "result";
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return getCatalogName(column);
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        // TODO (implement later) ?
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        // TODO (implement later) ?
        return 0;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        // TODO (implement)
        return "";
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        // TODO (implement)
        return "";
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        // TODO (result set)
        return Types.NVARCHAR;
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        // TODO (result set)
        return "String";
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        // TODO (result set)
        return "java.lang.String";
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
