package jdbc.resultset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

public class RedisEmptyResultSet extends RedisResultSetBase {

    @Override
    public boolean next() throws SQLException {
        checkClosed();
        return false;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        checkClosed();
        throw new SQLException("Exhausted ResultSet.");
    }

    // TODO (think): is it correct?
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        checkClosed();
        return new RedisResultSetMetaData(Collections.emptyList());
    }
}
