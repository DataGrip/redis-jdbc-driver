package jdbc.client.helpers.query.parser;

import jdbc.client.helpers.query.parser.lexer.Lexer;
import jdbc.client.structures.query.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static jdbc.Utils.getFirst;
import static jdbc.Utils.toUpperCase;

public class QueryParser {

    private QueryParser() {
    }


    private static final Map<String, Protocol.Command> COMMANDS =
            Arrays.stream(Protocol.Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Protocol.Keyword> KEYWORDS =
            Arrays.stream(Protocol.Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Protocol.Command> BLOCKING_COMMANDS = Set.of(
            Protocol.Command.BLMOVE, Protocol.Command.BLMPOP, Protocol.Command.BLPOP, Protocol.Command.BRPOP,
            Protocol.Command.BRPOPLPUSH, Protocol.Command.BZMPOP,Protocol.Command.BZPOPMAX, Protocol.Command.BZPOPMIN
    );

    private static final Set<Protocol.Command> COMMANDS_WITH_KEYWORDS = Set.of(
            Protocol.Command.ACL, Protocol.Command.CLIENT, Protocol.Command.CLUSTER, Protocol.Command.COMMAND,
            Protocol.Command.CONFIG, Protocol.Command.FUNCTION, Protocol.Command.MEMORY, Protocol.Command.MODULE,
            Protocol.Command.OBJECT, Protocol.Command.PUBSUB, Protocol.Command.SCRIPT, Protocol.Command.SLOWLOG,
            Protocol.Command.XGROUP, Protocol.Command.XINFO
    );

    private static final Map<Protocol.Command, Protocol.Keyword> COMMAND_RESULT_KEYWORDS = Map.of(
            Protocol.Command.ZDIFF, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZINTER, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZRANDMEMBER, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZRANGE, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZRANGEBYSCORE, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZREVRANGE, Protocol.Keyword.WITHSCORES,
            Protocol.Command.ZREVRANGEBYSCORE, Protocol.Keyword.WITHSCORES,
            Protocol.Command.HRANDFIELD, Protocol.Keyword.WITHVALUES
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
        Protocol.Command command = parseCommand(commandLine.command);
        Protocol.Keyword commandKeyword = parseCommandKeyword(command, commandLine.params);
        Protocol.Keyword resultKeyword = parseResultKeyword(command, commandLine.params);
        CompositeCommand compositeCommand = new CompositeCommand(command, commandKeyword, resultKeyword, commandLine.params);

        ColumnHintLine columnHintLine = rawQuery.columnHintLine;
        ColumnHint columnHint = columnHintLine == null ? null : new ColumnHint(columnHintLine.name, columnHintLine.values);

        return createQuery(compositeCommand, columnHint);
    }

    private static @NotNull Protocol.Command parseCommand(@NotNull String commandStr) throws SQLException {
        Protocol.Command command = getCommand(commandStr);
        if (command == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", commandStr));
        return command;
    }

    private static @Nullable Protocol.Keyword parseCommandKeyword(@NotNull Protocol.Command command,
                                                                  @NotNull String[] params) throws SQLException {
        if (!COMMANDS_WITH_KEYWORDS.contains(command)) return null;
        String commandKeywordStr = getFirst(params);
        if (commandKeywordStr == null)
            throw new SQLException(String.format("Query does not contain a keyword for the command %s.", command));
        Protocol.Keyword commandKeyword = getKeyword(commandKeywordStr);
        if (commandKeyword == null)
            throw new SQLException(String.format(
                    "Query contains an unknown keyword for the command %s: %s.",
                    command,
                    commandKeyword
            ));
        return commandKeyword;
    }

    private static @Nullable Protocol.Keyword parseResultKeyword(@NotNull Protocol.Command command,
                                                                 @NotNull String[] params) {
        Protocol.Keyword resultKeyword = COMMAND_RESULT_KEYWORDS.get(command);
        if (resultKeyword == null) return null;
        String resultKeywordStr = resultKeyword.name();
        return Arrays.stream(params).anyMatch(p -> resultKeywordStr.equals(toUpperCase(p))) ? resultKeyword : null;
    }


    private static @NotNull RedisQuery createQuery(@NotNull CompositeCommand compositeCommand,
                                                   @Nullable ColumnHint columnHint) throws SQLException {
        Protocol.Command command = compositeCommand.getCommand();
        if (command == Protocol.Command.SELECT) {
            String db = getFirst(compositeCommand.getParams());
            if (db == null) throw new SQLException("Database should be specified.");
            try {
                int dbIndex = Integer.parseInt(db);
                return new RedisSetDatabaseQuery(compositeCommand, dbIndex, columnHint);
            } catch (NumberFormatException e) {
                throw new SQLException(String.format("Database should be a number: %s.", db));
            }
        }
        if (BLOCKING_COMMANDS.contains(command)) {
            return new RedisBlockingQuery(compositeCommand, columnHint);
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
