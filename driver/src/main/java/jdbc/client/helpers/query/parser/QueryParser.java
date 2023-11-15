package jdbc.client.helpers.query.parser;

import jdbc.client.structures.query.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.commands.ProtocolCommand;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static jdbc.utils.Utils.*;

public class QueryParser {

    private QueryParser() {
    }


    private static final Set<ProtocolCommand> BLOCKING_COMMANDS = Set.of(
            Command.BLMOVE, Command.BLMPOP, Command.BLPOP, Command.BRPOP,
            Command.BRPOPLPUSH, Command.BZMPOP, Command.BZPOPMAX, Command.BZPOPMIN
    );


    public static @NotNull RedisQuery parse(@Nullable String sql) throws SQLException {
        if (sql == null) throw new SQLException("Empty query.");
        List<List<String>> tokens = Lexer.tokenize(sql);
        RawQuery rawQuery = createRawQuery(tokens);

        CompositeCommand compositeCommand = parseCompositeCommand(rawQuery.commandLine);
        String[] params = rawQuery.commandLine.params;
        ColumnHint columnHint = parseColumnHint(rawQuery.columnHintLine);
        NodeHint nodeHint = parseNodeHint(rawQuery.nodeHintLine);

        return createQuery(compositeCommand, params, columnHint, nodeHint);
    }

    private static @NotNull CompositeCommand parseCompositeCommand(@NotNull CommandLine commandLine) throws SQLException {
        String commandName = getName(commandLine.command);
        String[] params = commandLine.params;
        if (NativeCommandParser.accepts(commandName)) return new NativeCommandParser(commandName, params).parse();
        if (JsonCommandParser.accepts(commandName)) return new JsonCommandParser(commandName, params).parse();
        return new CompositeCommand(null, commandName, null);
    }

    private static @Nullable ColumnHint parseColumnHint(@Nullable ColumnHintLine columnHintLine) {
        return columnHintLine != null ? new ColumnHint(columnHintLine.name, columnHintLine.values) : null;
    }

    private static @Nullable NodeHint parseNodeHint(@Nullable NodeHintLine nodeHintLine) {
        if (nodeHintLine != null && nodeHintLine.hostAndPort != null) {
            try {
                HostAndPort hostAndPort = parseHostAndPort(nodeHintLine.hostAndPort);
                return new NodeHint(hostAndPort);
            }
            catch (Exception ignored) {
            }
        }
        return null;
    }


    // TODO (stack): refactor
    private static @NotNull RedisQuery createQuery(@NotNull CompositeCommand compositeCommand,
                                                   @NotNull String[] params,
                                                   @Nullable ColumnHint columnHint,
                                                   @Nullable NodeHint nodeHint) throws SQLException {
        ProtocolCommand command = compositeCommand.getCommand();
        boolean isBlocking = BLOCKING_COMMANDS.contains(command);

        // set databases query
        if (command == Command.SELECT && params.length == 1) {
            int dbIndex = parseSqlDbIndex(getFirst(params));
            return new RedisSetDatabaseQuery(compositeCommand, dbIndex, columnHint);
        }

        // keys pattern queries
        if (command == Command.KEYS) {
            String pattern = getFirst(params);
            return new RedisKeyPatternQuery(compositeCommand, params, pattern, columnHint, nodeHint, isBlocking);
        }
        if (command == Command.SCAN) {
            Integer matchIndex = getIndex(params, p -> Keyword.MATCH.name().equalsIgnoreCase(p));
            String pattern = matchIndex == null || matchIndex == params.length - 1 ? null : params[matchIndex + 1];
            return new RedisKeyPatternQuery(compositeCommand, params, pattern, columnHint, nodeHint, isBlocking);
        }

        return new RedisQuery(compositeCommand, params, columnHint, nodeHint, isBlocking);
    }


    private static @NotNull RawQuery createRawQuery(@NotNull List<List<String>> tokens) throws SQLException {
        List<Line> lines = tokens.stream().map(QueryParser::createLine).collect(Collectors.toList());

        List<CommandLine> commandLines = lines.stream()
                .map(l -> l instanceof CommandLine ? (CommandLine) l : null).filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (commandLines.isEmpty()) throw new SQLException("Query should contain a command.");
        if (commandLines.size() > 1) throw new SQLException("Query can contain only one command.");
        CommandLine commandLine = commandLines.get(0);

        ColumnHintLine columnHintLine =
                getHintLine(lines, l -> l instanceof ColumnHintLine ? (ColumnHintLine) l : null, "column");
        NodeHintLine nodeHintLine =
                getHintLine(lines, l -> l instanceof NodeHintLine ? (NodeHintLine) l : null, "node");

        return new RawQuery(commandLine, columnHintLine, nodeHintLine);
    }

    private static @Nullable Line createLine(@NotNull List<String> lineTokens) {
        if (NodeHintLine.accepts(lineTokens)) return new NodeHintLine(lineTokens);
        if (ColumnHintLine.accepts(lineTokens)) return new ColumnHintLine(lineTokens);
        if (CommentLine.accepts(lineTokens)) return new CommentLine(lineTokens);
        if (CommandLine.accepts(lineTokens)) return new CommandLine(lineTokens);
        return null;
    }

    private static <T extends HintLine> @Nullable T getHintLine(@NotNull List<Line> lines,
                                                                @NotNull Function<Line, T> lineMapper,
                                                                @NotNull String hintType) throws SQLException {
        List<T> hintLines = lines.stream().map(lineMapper).filter(Objects::nonNull).collect(Collectors.toList());
        if (hintLines.size() > 1)
            throw new SQLException(String.format("Query can contain only one comment with %s hint.", hintType));
        return hintLines.isEmpty() ? null : hintLines.get(0);
    }


    private static class RawQuery {
        public final CommandLine commandLine;
        public final ColumnHintLine columnHintLine;
        public final NodeHintLine nodeHintLine;

        RawQuery(@NotNull CommandLine commandLine,
                 @Nullable ColumnHintLine columnHintLine,
                 @Nullable QueryParser.NodeHintLine nodeHintLine) {
            this.commandLine = commandLine;
            this.columnHintLine = columnHintLine;
            this.nodeHintLine = nodeHintLine;
        }
    }

    private interface Line {}

    private interface HintLine extends Line {}

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

    private static class ColumnHintLine extends CommentLine implements HintLine {
        private static final String SEPARATOR_TOKEN = ":";

        public final @NotNull String name;
        public final @NotNull String[] values;

        ColumnHintLine(@NotNull List<String> tokens) {
            super(tokens);
            if (!accepts(tokens)) throw new AssertionError(String.format("Incorrect column hint tokens: %s.", tokens));
            name = tokens.get(1);
            values = tokens.stream().skip(3).toArray(String[]::new);
        }

        public static boolean accepts(@NotNull List<String> tokens) {
            return CommentLine.accepts(tokens)
                    && tokens.size() >= 3
                    && SEPARATOR_TOKEN.equals(tokens.get(2));
        }
    }

    private static class NodeHintLine extends CommentLine implements HintLine {
        private static final String NODE_TOKEN = "node";
        private static final String SEPARATOR_TOKEN = "=";

        public final @Nullable String hostAndPort;

        NodeHintLine(@NotNull List<String> tokens) {
            super(tokens);
            hostAndPort = tokens.get(3);
        }

        public static boolean accepts(@NotNull List<String> tokens) {
            return CommentLine.accepts(tokens)
                    && tokens.size() == 4
                    && NODE_TOKEN.equalsIgnoreCase(tokens.get(1))
                    && SEPARATOR_TOKEN.equals(tokens.get(2));
        }
    }
}
