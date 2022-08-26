package jdbc.client;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Client extends AutoCloseable {

    ResultSet execute(String sql) throws SQLException;

    void setDatabase(String db) throws SQLException;

    String getDatabase();

    void close() throws SQLException;
}