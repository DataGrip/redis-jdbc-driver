package jdbc.client.helpers.query;

import jdbc.client.helpers.query.parser.QueryParser;
import jdbc.client.structures.query.RedisQuery;
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
