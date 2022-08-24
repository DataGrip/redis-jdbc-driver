package jdbc.resultset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.List;

public class RedisResultSetMetaData implements ResultSetMetaData {

    // TODO (result set): catalog and table name?
    private static final String NOT_APPLICABLE_TABLE_NAME = "";
    private static final String NOT_APPLICABLE_CATALOG = "";
    private static final int NOT_APPLICABLE_SCALE = 0;
    private static final int NOT_APPLICABLE_PRECISION = 0;

    private final List<ColumnMetaData> columnMetaData;

    public RedisResultSetMetaData(List<ColumnMetaData> columnMetaData) {
        this.columnMetaData = columnMetaData;
    }

    public static ColumnMetaData createColumn(String name) {
        return new ColumnMetaData(name);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnMetaData.size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
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
        return columnMetaData.get(column - 1).name;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columnMetaData.get(column - 1).name;
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        return getCatalogName(column);
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        return NOT_APPLICABLE_PRECISION;
    }

    @Override
    public int getScale(int column) throws SQLException {
        return NOT_APPLICABLE_SCALE;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        return NOT_APPLICABLE_TABLE_NAME;
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        return NOT_APPLICABLE_CATALOG;
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        return columnMetaData.get(column - 1).getJavaType();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return columnMetaData.get(column - 1).typeName;
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
        return columnMetaData.get(column - 1).getClassName();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    // TODO (result set): types
    public static class ColumnMetaData {
        private final String name;
        private final String typeName;

        private ColumnMetaData(String name) {
            this.name = name;
            this.typeName = "string";
        }

        public int getJavaType() {
            return Types.VARCHAR;
        }

        public String getClassName() {
            return "java.lang.String";
        }
    }
}
