package jdbc.client.query;

import jdbc.client.query.parser.QueryParser;
import jdbc.client.query.structures.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;

public class RedisQueryHelper {

    private RedisQueryHelper() {
    }

    public static @NotNull RedisQuery parseQuery(@Nullable String sql) throws SQLException {
        return QueryParser.parse(sql);
    }
}
