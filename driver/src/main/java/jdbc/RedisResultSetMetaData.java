package jdbc;

import jdbc.types.RedisColumnTypeHelper;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisResultSetMetaData implements ResultSetMetaData {

    // TODO (result set): catalog and table name?
    private static final String NOT_APPLICABLE_TABLE_NAME = "";
    private static final String NOT_APPLICABLE_CATALOG = "";
    private static final int NOT_APPLICABLE_SCALE = 0;
    private static final int NOT_APPLICABLE_PRECISION = 0;

    private final List<ColumnMetaData> columnMetaData;

    public RedisResultSetMetaData(List<Map<String, Object>> rows) {
        if (rows.isEmpty()) {
            columnMetaData = Collections.emptyList();
            return;
        }
        Map<String, Object> row = rows.get(0);
        this.columnMetaData = row.entrySet().stream().map(e -> createColumn(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    // TODO (result set): slow
    public int findColumn(String columnLabel) {
        for (int i = 0; i < columnMetaData.size(); i++) {
            if (columnMetaData.get(i).name.equals(columnLabel)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static ColumnMetaData createColumn(String name, Object value) {
        return new ColumnMetaData(name, value);
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

        private ColumnMetaData(String name, Object value) {
            this.name = name;
            this.typeName = getTypeName(value);
        }

        private static String getTypeName(Object object) {
            String typeName = "string";
            if (object == null) {
                typeName = "null";
            } else if (object instanceof List<?> || object.getClass().isArray()) {
                typeName = "array";
            } else if (object instanceof Map<?, ?>) {
                typeName = "object";
            } else if (object instanceof Boolean) {
                typeName = "boolean";
            } else if (object instanceof Float) {
                typeName = "float";
            } else if (object instanceof Double) {
                typeName = "double";
            } else if (object instanceof Long) {
                typeName = "long";
            } else if (object instanceof Integer) {
                typeName = "integer";
            } else if (object instanceof Number) {
                typeName = "numeric";
            }
            return typeName;
        }

        public int getJavaType() {
            return RedisColumnTypeHelper.getJavaType(typeName);
        }

        public String getClassName() {
            return RedisColumnTypeHelper.getClassName(typeName);
        }
    }
}
