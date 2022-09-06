package jdbc.resultset;

import jdbc.resultset.types.RedisColumnTypeHelper;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public class RedisResultSetMetaData implements ResultSetMetaData {

    // TODO (implement later): catalog and table name?
    private static final String NOT_APPLICABLE_TABLE_NAME = "";
    private static final String NOT_APPLICABLE_CATALOG = "";
    private static final int NOT_APPLICABLE_SCALE = 0;
    private static final int NOT_APPLICABLE_PRECISION = 0;

    private final ColumnMetaData[] columnMetaData;

    // TODO (null)
    public RedisResultSetMetaData(ColumnMetaData... columnMetaData) {
        this.columnMetaData = columnMetaData;
    }

    public int findColumn(String columnLabel) {
        for (int i = 0; i < columnMetaData.length; i++) {
            if (columnMetaData[i].name.equals(columnLabel)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static ColumnMetaData createColumn(String name, String typeName) {
        return new ColumnMetaData(name, typeName);
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnMetaData.length;
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
        return columnMetaData[column - 1].name;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columnMetaData[column - 1].name;
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
        return columnMetaData[column - 1].getJavaType();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return columnMetaData[column - 1].typeName;
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
        return columnMetaData[column - 1].getClassName();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    public static class ColumnMetaData {
        private final String name;
        private final String typeName;

        private ColumnMetaData(String name, String typeName) {
            this.name = name;
            this.typeName = typeName;
        }

        public int getJavaType() {
            return RedisColumnTypeHelper.getJavaType(typeName);
        }

        public String getClassName() {
            return RedisColumnTypeHelper.getClassName(typeName);
        }
    }
}
