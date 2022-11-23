package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class IdentityConverter<T> extends SimpleConverter<T> {

    @Override
    protected @NotNull T convertImpl(@NotNull T encoded) {
        return encoded;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<Object> convertImpl(@NotNull List<T> encoded) {
        return (List<Object>) encoded;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Map<String, Object> convertImpl(@NotNull Map<String, T> encoded) {
        return (Map<String, Object>) encoded;
    }
}
