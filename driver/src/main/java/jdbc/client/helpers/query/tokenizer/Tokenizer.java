package jdbc.client.helpers.query.tokenizer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private Tokenizer() {
    }

    private abstract static class State {
        public final String token;

        protected State(@Nullable String token) {
            this.token = token;
        }

        public abstract @NotNull State process(char symbol);
    }

    private static class None extends State {
        public None(@Nullable String token) {
            super(token);
        }

        @Override
        public @NotNull State process(char symbol) {
            if (isBlank(symbol)) return new None(null);
            if (isQuote(symbol)) return new QuotedIdentifier(symbol, null);
            return new PlainIdentifier(symbol, isEscaping(symbol), null);
        }
    }

    private abstract static class Identifier extends State {
        public final StringBuilder builder;
        public final boolean isEscaping;

        public Identifier(@NotNull StringBuilder builder, char symbol, boolean isEscaping, @Nullable String token) {
            super(token);
            this.builder = isEscaping || symbol == 0 ? builder : builder.append(symbol);
            this.isEscaping = isEscaping;
        }
    }

    private static class PlainIdentifier extends Identifier {
        public PlainIdentifier(@NotNull StringBuilder builder, char symbol, boolean isEscaping, @Nullable String token) {
            super(builder, symbol, isEscaping, token);
        }

        public PlainIdentifier(char symbol, boolean isEscaping, @Nullable String token) {
            this(new StringBuilder(), symbol, isEscaping, token);
        }

        @Override
        public @NotNull State process(char symbol) {
            if (isBlank(symbol)) return new None(builder.toString());
            if (isQuote(symbol) && !isEscaping) new QuotedIdentifier(symbol, builder.toString());
            return new PlainIdentifier(builder, symbol, isEscaping(symbol) && !isEscaping, null);
        }
    }

    private static class QuotedIdentifier extends Identifier {
        public final char quote;

        public QuotedIdentifier(char quote, @NotNull StringBuilder builder, char symbol, boolean isEscaping, @Nullable String token) {
            super(builder, symbol, isEscaping, token);
            this.quote = quote;
        }

        public QuotedIdentifier(char quote, @Nullable String token) {
            this(quote, new StringBuilder(), (char)0, false, token);
        }

        @Override
        public @NotNull State process(char symbol) {
            if (symbol == quote && !isEscaping) return new None(builder.toString());
            return new QuotedIdentifier(quote, builder, symbol, isEscaping(symbol) && !isEscaping, null);
        }
    }

    public static List<String> tokenize(@NotNull String sql) throws SQLException {
        List<String> tokens = new ArrayList<>();
        State state = new None(null);
        char[] symbols = sql.toCharArray();
        for (int i = 0; i <= symbols.length; ++i) {
            char symbol = i == symbols.length ? 0 : symbols[i];
            state = state.process(symbol);
            String token = state.token;
            if (token != null) {
                tokens.add(token);
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
