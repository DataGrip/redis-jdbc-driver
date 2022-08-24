package jdbc.resultset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisListResultSet extends RedisResultSetBase {

    private final List<?> data;
    private int currentRow = -1;

    public RedisListResultSet(List<?> data) {
        this.data = data;
    }

    @Override
    public boolean next() throws SQLException {
        checkClosed();
        if (currentRow < data.size() - 1) {
            ++currentRow;
            return true;
        }
        return false;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        checkClosed();
        if (currentRow >= data.size()) throw new SQLException("Exhausted ResultSet.");
        if (columnIndex > 1) throw new SQLException(String.format("Incorrect column index: %d", columnIndex));
        return data.get(currentRow).toString();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        checkClosed();
        return new RedisResultSetMetaData(Collections.singletonList(createColumn("value")));
    }
}
