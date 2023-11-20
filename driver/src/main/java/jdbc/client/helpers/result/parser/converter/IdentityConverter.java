package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public abstract class IdentityConverter<T> extends SimpleConverter<T, T> {
    @Override
    protected @NotNull T convertImpl(@NotNull T encoded) {
        return encoded;
    }

    @Override
    public @NotNull List<T> convertListImpl(@NotNull List<T> encoded) {
        return encoded;
    }

    @Override
    public @NotNull Map<String, T> convertMapImpl(@NotNull Map<String, T> encoded) {
        return encoded;
    }
}
