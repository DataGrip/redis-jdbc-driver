package jdbc.resultset.types;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.stream.StreamSupport;


public class ArrayImpl implements Array {
    private Object[] array;

    public ArrayImpl(Iterable<?> array) {
        this.array = StreamSupport.stream(array.spliterator(), false).toArray();
    }

    @Override
    public String getBaseTypeName() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getBaseType() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Object[] getArray() throws SQLException {
        if (array == null) throw new SQLException("Array was freed");
        return array;
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Object getArray(long index, int count) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void free() {
        if (array != null) {
            array = null;
        }
    }
}