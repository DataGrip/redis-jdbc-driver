package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.Contract;
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

    @Contract("null -> null; !null -> !null")
    public final List<V> convert(List<T> encoded) {
        return encoded != null
                ? encoded.stream().map(this::convert).collect(Collectors.toList())
                : null;
    }

    @Contract("null -> null; !null -> !null")
    public final Map<String, V> convert(Map<String, T> encoded) {
        return encoded != null
                ? encoded.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertImpl(e.getValue())))
                : null;
    }
}
