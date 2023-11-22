package jdbc.resultset;

import jdbc.client.result.structures.SimpleType;
import jdbc.types.RedisColumnTypeHelper;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;

public class RedisResultSetMetaData implements ResultSetMetaData {

    // TODO (implement later): catalog and table name?
    private static final String NOT_APPLICABLE_TABLE_NAME = "";
    private static final String NOT_APPLICABLE_CATALOG = "";
    private static final int NOT_APPLICABLE_SCALE = 0;
    private static final int NOT_APPLICABLE_PRECISION = 0;

    private final List<ColumnMetaData> columnMetaDatas;

    public RedisResultSetMetaData(@NotNull List<ColumnMetaData> columnMetaDatas) {
        this.columnMetaDatas = columnMetaDatas;
    }

    public static ColumnMetaData createColumn(@NotNull String name, @NotNull String typeName) {
        return new ColumnMetaData(name, typeName);
    }

    public static ColumnMetaData createColumn(@NotNull String name, @NotNull SimpleType<?> type) {
        return new ColumnMetaData(name, type.getTypeName());
    }

    public int findColumn(String columnLabel) {
        for (int i = 0; i < columnMetaDatas.size(); ++i) {
            if (columnMetaDatas.get(i).name.equals(columnLabel)) {
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return columnMetaDatas.size();
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
        return columnMetaDatas.get(column - 1).name;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return columnMetaDatas.get(column - 1).name;
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
        return columnMetaDatas.get(column - 1).getJavaType();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        return columnMetaDatas.get(column - 1).typeName;
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
        return columnMetaDatas.get(column - 1).getClassName();
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

        private ColumnMetaData(@NotNull String name, @NotNull String typeName) {
            this.name = name;
            this.typeName = typeName;
        }

        public @NotNull String getName() {
            return name;
        }

        public int getJavaType() {
            return RedisColumnTypeHelper.getJavaType(typeName);
        }

        public String getClassName() {
            return RedisColumnTypeHelper.getClassName(typeName);
        }
    }
}
