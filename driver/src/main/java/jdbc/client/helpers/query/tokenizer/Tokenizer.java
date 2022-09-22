package jdbc.client.helpers.query.tokenizer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private Tokenizer() {
    }

    private static class Token {
        public final int begin;
        public final int end;

        public Token(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }
    }

    private abstract static class State {
        public final Token token;

        protected State(@Nullable Token token) {
            this.token = token;
        }

        public abstract @NotNull State process(int index, char symbol);
    }

    private static class None extends State {
        public None(@Nullable Token token) {
            super(token);
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (isBlank(symbol)) return new None(null);
            if (isQuote(symbol)) return new QuotedIdentifier(null, index, symbol, false);
            return new PlainIdentifier(null, index, isEscaping(symbol));
        }
    }

    private abstract static class Identifier extends State {
        public final int begin;
        public final boolean isEscaped;

        public Identifier(@Nullable Token token, int begin, boolean isEscaped) {
            super(token);
            this.begin = begin;
            this.isEscaped = isEscaped;
        }

        protected final Token complete(int end) {
            return new Token(begin, end);
        }
    }

    private static class PlainIdentifier extends Identifier {
        public PlainIdentifier(@Nullable Token token, int start, boolean isEscaped) {
            super(token, start, isEscaped);
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (isBlank(symbol)) return new None(complete(index));
            if (isQuote(symbol) && !isEscaped) new QuotedIdentifier(complete(index), index, symbol, false);
            return new PlainIdentifier(null, begin, isEscaping(symbol) && !isEscaped);
        }
    }

    private static class QuotedIdentifier extends Identifier {
        public final char quote;

        public QuotedIdentifier(@Nullable Token token, int start, char quote, boolean isEscaped) {
            super(token, start, isEscaped);
            this.quote = quote;
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (symbol == quote && !isEscaped) return new None(complete(index + 1));
            return new QuotedIdentifier(null, begin, quote, isEscaping(symbol) && !isEscaped);
        }
    }

    public static List<String> tokenize(@NotNull String sql) throws SQLException {
        List<String> tokens = new ArrayList<>();
        State state = new None(null);
        char[] symbols = sql.toCharArray();
        for (int i = 0; i <= symbols.length; ++i) {
            char symbol = i == symbols.length ? 0 : symbols[i];
            state = state.process(i, symbol);
            Token token = state.token;
            if (token != null) {
                tokens.add(sql.substring(token.begin, token.end));
            }
        }
        if (!(state instanceof None)) throw new SQLException("No closing quotation.");
        return tokens;
    }

    private static boolean isBlank(char c) {
        return Character.isWhitespace(c) || c == 0;
    }

    private static boolean isQuote(char c) {
        return c == '\'' || c == '"';
    }

    private static boolean isEscaping(char c) {
        return c == '\\';
    }
}
