package jdbc.client.helpers.query.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private Tokenizer() {
    }

    private interface State {
        @NotNull State process(int index, char symbol);
    }

    private static class None implements State {
        public static None INSTANCE = new None();

        private None() {
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (isBlank(symbol)) return None.INSTANCE;
            if (isQuote(symbol)) return new QuotedIdentifier(index, symbol, false);
            return new PlainIdentifier(index, isEscaping(symbol));
        }
    }

    private abstract static class Identifier implements State {
        public final int start;
        public final boolean isEscaped;
        public int end = -1;

        public Identifier(int start, boolean isEscaped) {
            this.start = start;
            this.isEscaped = isEscaped;
        }

        public void complete(int end) {
            this.end = end;
        }
    }

    private static class PlainIdentifier extends Identifier {
        public PlainIdentifier(int start, boolean isEscaped) {
            super(start, isEscaped);
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (isBlank(symbol)) {
                complete(index);
                return None.INSTANCE;
            }
            if (isQuote(symbol) && !isEscaped) {
                complete(index);
                return new QuotedIdentifier(index, symbol, false);
            }
            return new PlainIdentifier(start, isEscaping(symbol));
        }
    }

    private static class QuotedIdentifier extends Identifier {
        public final char quote;

        public QuotedIdentifier(int start, char quote, boolean isEscaped) {
            super(start, isEscaped);
            this.quote = quote;
        }

        @Override
        public @NotNull State process(int index, char symbol) {
            if (symbol == quote && !isEscaped) {
                complete(index + 1);
                return None.INSTANCE;
            }
            return new QuotedIdentifier(index, quote, isEscaping(symbol));
        }
    }

    public static List<String> tokenize(@NotNull String sql) throws SQLException {
        List<String> tokens = new ArrayList<>();
        State state = None.INSTANCE;
        char[] symbols = sql.toCharArray();
        for (int i = 0; i <= symbols.length; ++i) {
            char symbol = i == symbols.length ? 0 : symbols[i];
            State newState = state.process(i, symbol);
            if (state instanceof Identifier) {
                Identifier idState = (Identifier) state;
                if (idState.end != -1) {
                    tokens.add(sql.substring(idState.start, idState.end));
                }
            }
            state = newState;
        }
        if (state != None.INSTANCE) throw new SQLException("No closing quotation.");
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
