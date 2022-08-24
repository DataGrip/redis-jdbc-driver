package jdbc.resultset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static jdbc.resultset.RedisResultSetMetaData.createColumn;

public class RedisMapResultSet extends RedisResultSetBase {

    // TODO (think): order?
    private final List<Map.Entry<?, ?>> data;
    private int currentRow = -1;

    public RedisMapResultSet(Map<?, ?> data) {
        this.data = new ArrayList<>(data.entrySet());
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
        if (columnIndex > 2) throw new SQLException(String.format("Incorrect column index: %d", columnIndex));
        Map.Entry<?, ?> entry = data.get(currentRow);
        return columnIndex == 1 ? entry.getKey().toString() : entry.getValue().toString();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        checkClosed();
        return new RedisResultSetMetaData(Arrays.asList(createColumn("field"), createColumn("value")));
    }
}
