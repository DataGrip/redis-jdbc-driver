package jdbc;

import java.sql.SQLException;
import java.util.List;

public interface Client extends AutoCloseable {

    List<?> execute(String sql) throws SQLException;

    void setDatabase(String db) throws SQLException;

    String getDatabase();

    void close() throws SQLException;
}