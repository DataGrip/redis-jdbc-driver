package jdbc.client.helpers.query.parser;

import jdbc.client.helpers.query.parser.lexer.Lexer;
import jdbc.client.structures.query.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Protocol.ClusterKeyword;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.args.Rawable;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static jdbc.utils.Utils.*;

public class QueryParser {

    private QueryParser() {
    }


    private static final Map<String, Command> COMMANDS =
            Arrays.stream(Command.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, Keyword> KEYWORDS =
            Arrays.stream(Keyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Map<String, ClusterKeyword> CLUSTER_KEYWORDS =
            Arrays.stream(ClusterKeyword.values()).collect(Collectors.toMap(Enum::name, v -> v));

    private static final Set<Command> BLOCKING_COMMANDS = Set.of(
            Command.BLMOVE, Command.BLMPOP, Command.BLPOP, Command.BRPOP,
            Command.BRPOPLPUSH, Command.BZMPOP, Command.BZPOPMAX, Command.BZPOPMIN
    );

    private static final Set<Command> COMMANDS_WITH_KEYWORDS = Set.of(
            Command.ACL, Command.CLIENT, Command.CLUSTER, Command.COMMAND,
            Command.CONFIG, Command.FUNCTION, Command.MEMORY, Command.MODULE,
            Command.OBJECT, Command.PUBSUB, Command.SCRIPT, Command.SLOWLOG,
            Command.XGROUP, Command.XINFO
    );

    private static @Nullable Command getCommand(@NotNull String command) {
        return COMMANDS.get(getName(command));
    }

    // TODO (cluster): check mode
    @SuppressWarnings("RedundantIfStatement")
    private static @Nullable Rawable getKeyword(@NotNull String keyword) {
        String name = getName(keyword);
        Keyword knownKeyword = KEYWORDS.get(name);
        if (knownKeyword != null) return knownKeyword;
        ClusterKeyword clusterKeyword = CLUSTER_KEYWORDS.get(name);
        if (clusterKeyword != null) return clusterKeyword;
        return null;
    }


    public static @NotNull RedisQuery parse(@Nullable String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        List<List<String>> tokens = Lexer.tokenize(sql);
        RawQuery rawQuery = createRawQuery(tokens);

        CommandLine commandLine = rawQuery.commandLine;
        Command command = parseCommand(commandLine.command);
        Rawable commandKeyword = parseCommandKeyword(command, commandLine.params);
        CompositeCommand compositeCommand = new CompositeCommand(command, commandKeyword);

        ColumnHintLine columnHintLine = rawQuery.columnHintLine;
        ColumnHint columnHint = columnHintLine == null ? null : new ColumnHint(columnHintLine.name, columnHintLine.values);

        return createQuery(compositeCommand, commandLine.params, columnHint);
    }

    private static @NotNull Command parseCommand(@NotNull String commandStr) throws SQLException {
        Command command = getCommand(commandStr);
        if (command == null)
            throw new SQLException(String.format("Query contains an unknown command: %s.", commandStr));
        return command;
    }

    private static @Nullable Rawable parseCommandKeyword(@NotNull Command command,
                                                         @NotNull String[] params) throws SQLException {
        if (!COMMANDS_WITH_KEYWORDS.contains(command)) return null;
        String commandKeywordStr = getFirst(params);
        if (commandKeywordStr == null)
            throw new SQLException(String.format("Query does not contain a keyword for the command %s.", command));
        Rawable commandKeyword = getKeyword(commandKeywordStr);
        if (commandKeyword == null)
            throw new SQLException(String.format(
                    "Query contains an unknown keyword for the command %s: %s.",
                    command,
                    commandKeywordStr
            ));
        return commandKeyword;
    }

    private static @NotNull RedisQuery createQuery(@NotNull CompositeCommand compositeCommand,
                                                   @NotNull String[] params,
                                                   @Nullable ColumnHint columnHint) throws SQLException {
        Command command = compositeCommand.getCommand();
        if (command == Command.SELECT && params.length == 1) {
            return new RedisSetDatabaseQuery(compositeCommand, parseSqlDbIndex(getFirst(params)), columnHint);
        }
        if (BLOCKING_COMMANDS.contains(command)) {
            return new RedisBlockingQuery(compositeCommand, params, columnHint);
        }
        return new RedisQuery(compositeCommand, params, columnHint);
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
