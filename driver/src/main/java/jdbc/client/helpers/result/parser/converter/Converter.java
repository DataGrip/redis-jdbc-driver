package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class Converter<T, V> {

    protected abstract @NotNull V convertImpl(@NotNull T encoded);

    public final @Nullable V convert(@Nullable T encoded) {
        return encoded != null ? convertImpl(encoded) : null;
    }

    public final @NotNull List<V> convert(@NotNull List<T> encoded) {
        return encoded.stream().map(this::convert).collect(Collectors.toList());
    }

    public final @NotNull Map<String, V> convert(@NotNull Map<String, T> encoded) {
        return encoded.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertImpl(e.getValue())));
    }
}
