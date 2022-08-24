package jdbc.resultset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;

import static jdbc.resultset.RedisObjectResultSet.State.*;
import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisObjectResultSet extends RedisResultSetBase {

    enum State {INITIAL, OPENED, EXHAUSTED}

    private final Object data;
    private State state = INITIAL;

    public RedisObjectResultSet(Object data) {
        this.data = data;
    }

    @Override
    public boolean next() throws SQLException {
        checkClosed();
        if (state == INITIAL) {
            state = OPENED;
            return true;
        }
        state = EXHAUSTED;
        return false;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        checkClosed();
        if (state == EXHAUSTED) throw new SQLException("Exhausted ResultSet.");
        if (columnIndex > 1) throw new SQLException(String.format("Incorrect column index: %d", columnIndex));
        return data.toString();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        checkClosed();
        return new RedisResultSetMetaData(Collections.singletonList(createColumn("value")));
    }
}
