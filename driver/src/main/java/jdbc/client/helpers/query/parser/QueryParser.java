package jdbc.client.helpers.query.parser;

import jdbc.client.helpers.query.parser.lexer.Lexer;
import jdbc.client.structures.query.ColumnHint;
import jdbc.client.structures.query.CompositeCommand;
import jdbc.client.structures.query.RedisQuery;
import jdbc.client.structures.query.RedisSetDatabaseQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static jdbc.Utils.*;

public class QueryParser {

    private QueryParser() {
    }


    private static final Map<String, Protocol.Command> COMMANDS =
            Arrays.stream(Protocol.Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Protocol.Keyword> KEYWORDS =
            Arrays.stream(Protocol.Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Protocol.Command> COMMANDS_WITH_PREFIX_KEYWORDS = Set.of(
            Protocol.Command.ACL, Protocol.Command.CLIENT, Protocol.Command.CLUSTER, Protocol.Command.CONFIG,
            Protocol.Command.MEMORY, Protocol.Command.MODULE, Protocol.Command.OBJECT, Protocol.Command.PUBSUB,
            Protocol.Command.SCRIPT, Protocol.Command.SLOWLOG, Protocol.Command.XGROUP, Protocol.Command.XINFO
    );

    private static final Set<Protocol.Command> COMMANDS_WITH_POSTFIX_KEYWORDS = Set.of(
            Protocol.Command.ZDIFF, Protocol.Command.ZINTER, Protocol.Command.ZRANDMEMBER, Protocol.Command.ZRANGE, Protocol.Command.ZRANGEBYSCORE,
            Protocol.Command.ZREVRANGE, Protocol.Command.ZREVRANGEBYSCORE, Protocol.Command.HRANDFIELD
    );

    private static @Nullable Protocol.Command getCommand(@NotNull String command) {
        return COMMANDS.get(toUpperCase(command));
    }

    private static @Nullable Protocol.Keyword getKeyword(@NotNull String keyword) {
        return KEYWORDS.get(toUpperCase(keyword));
    }


    public static @NotNull RedisQuery parse(@Nullable String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        List<List<String>> tokens = Lexer.tokenize(sql);
        RawQuery rawQuery = createRawQuery(tokens);

        CommandLine commandLine = rawQuery.commandLine;
        Protocol.Command redisCommand = parseCommand(commandLine.command);
        Protocol.Keyword redisKeyword = parseKeyword(redisCommand, commandLine.params);
        CompositeCommand compositeCommand = new CompositeCommand(redisCommand, redisKeyword, commandLine.params);

        ColumnHintLine columnHintLine = rawQuery.columnHintLine;
        ColumnHint columnHint = columnHintLine == null ? null : new ColumnHint(columnHintLine.name, columnHintLine.values);

        return createQuery(compositeCommand, columnHint);
    }

    private static @NotNull Protocol.Command parseCommand(@NotNull String command) throws SQLException {
        Protocol.Command redisCommand = getCommand(command);
        if (redisCommand == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", command));
        return redisCommand;
    }

    private static @Nullable Protocol.Keyword parseKeyword(@NotNull Protocol.Command redisCommand,
                                                           @NotNull String[] params) throws SQLException {
        if (COMMANDS_WITH_PREFIX_KEYWORDS.contains(redisCommand)) {
            return parsePrefixKeyword(redisCommand, getFirst(params));
        }
        if (COMMANDS_WITH_POSTFIX_KEYWORDS.contains(redisCommand)) {
            return parseSuffixKeyword(redisCommand, getLast(params));
        }
        return null;
    }

    private static @NotNull Protocol.Keyword parsePrefixKeyword(@NotNull Protocol.Command redisCommand,
                                                                @Nullable String keyword) throws SQLException {
        if (keyword == null)
            throw new SQLException(String.format("Query does not contain a keyword for the command %s.", redisCommand));
        Protocol.Keyword redisKeyword = getKeyword(keyword);
        if (redisKeyword == null)
            throw new SQLException(String.format(
                    "Query contains an unknown keyword for the command %s: %s.",
                    redisCommand,
                    keyword
            ));
        return redisKeyword;
    }

    private static @Nullable Protocol.Keyword parseSuffixKeyword(@NotNull Protocol.Command redisCommand, @Nullable String keyword) {
        return keyword == null ? null : getKeyword(keyword);
    }


    private static @NotNull RedisQuery createQuery(@NotNull CompositeCommand compositeCommand,
                                                   @Nullable ColumnHint columnHint) throws SQLException {
        if (compositeCommand.getCommand() == Protocol.Command.SELECT) {
            String db = getFirst(compositeCommand.getParams());
            if (db == null) throw new SQLException("Database should be specified.");
            try {
                int dbIndex = Integer.parseInt(db);
                return new RedisSetDatabaseQuery(compositeCommand, dbIndex, columnHint);
            } catch (NumberFormatException e) {
                throw new SQLException(String.format("Database should be a number: %s.", db));
            }
        }
        return new RedisQuery(compositeCommand, columnHint);
    }


    private static @NotNull RawQuery createRawQuery(@NotNull List<List<String>> tokens) throws SQLException {
        List<Line> lines = tokens.stream().map(QueryParser::createLine).collect(Collectors.toList());

        List<CommandLine> commandLines = lines.stream()
                .map(l -> l instanceof CommandLine ? (CommandLine) l : null).filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (commandLines.isEmpty()) throw new SQLException("Query should contain a command.");
        if (commandLines.size() > 1) throw new SQLException("Query can contain only one command.");
        CommandLine commandLine = commandLines.get(0);

        List<ColumnHintLine> columnHintLines = lines.stream()
                .map(l -> l instanceof ColumnHintLine? (ColumnHintLine) l : null).filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (columnHintLines.size() > 1) throw new SQLException("Query can contain only one comment with column hint.");
        ColumnHintLine columnHintLine = columnHintLines.isEmpty() ? null : columnHintLines.get(0);

        return new RawQuery(commandLine, columnHintLine);
    }

    private static @Nullable Line createLine(@NotNull List<String> lineTokens) {
        if (ColumnHintLine.accepts(lineTokens)) return new ColumnHintLine(lineTokens);
        if (CommentLine.accepts(lineTokens)) return new CommentLine(lineTokens);
        if (CommandLine.accepts(lineTokens)) return new CommandLine(lineTokens);
        return null;
    }


    private static class RawQuery {
        public final CommandLine commandLine;
        public final ColumnHintLine columnHintLine;

        RawQuery(@NotNull CommandLine commandLine, @Nullable ColumnHintLine columnHintLine) {
            this.commandLine = commandLine;
            this.columnHintLine = columnHintLine;
        }
    }

    private interface Line {}

    private static class CommandLine implements Line {
        public final String command;
        public final String[] params;

        CommandLine(@NotNull List<String> tokens) {
            if (!accepts(tokens)) throw new AssertionError(String.format("Incorrect command tokens: %s.", tokens));
            command = tokens.get(0);
            params = tokens.stream().skip(1).toArray(String[]::new);
        }

        public static boolean accepts(@NotNull List<String> tokens) {
            return tokens.size() >= 1;
        }
    }

    private static class CommentLine implements Line {
        private static final String COMMENT_TOKEN = "--";

        CommentLine(@NotNull List<String> tokens) {
            if (!accepts(tokens)) throw new AssertionError(String.format("Incorrect comment tokens: %s.", tokens));
        }

        public static boolean accepts(@NotNull List<String> tokens) {
            return tokens.size() >= 1 && COMMENT_TOKEN.equals(tokens.get(0));
        }
    }

    private static class ColumnHintLine extends CommentLine {
        private static final String COLUMN_NAME_SEPARATOR_TOKEN = ":";

        public final String name;
        public final String[] values;

        ColumnHintLine(@NotNull List<String> tokens) {
            super(tokens);
            if (!accepts(tokens)) throw new AssertionError(String.format("Incorrect column hint tokens: %s.", tokens));
            name = tokens.get(1);
            values = tokens.stream().skip(3).toArray(String[]::new);
        }

        public static boolean accepts(@NotNull List<String> tokens) {
            return CommentLine.accepts(tokens) && tokens.size() >= 3 && COLUMN_NAME_SEPARATOR_TOKEN.equals(tokens.get(2));
        }
    }
}
