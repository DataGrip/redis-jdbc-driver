package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class Converter<T, V, MV> {

    @Contract("null -> null; !null -> !null")
    public final V convert(T encoded) {
        return encoded != null ? convertImpl(encoded) : null;
    }

    protected abstract @NotNull V convertImpl(@NotNull T encoded);

    @Contract("null -> null; !null -> !null")
    public final List<V> convert(List<T> encoded) {
        return encoded != null ? convertImpl(encoded) : null;
    }

    protected @NotNull List<V> convertImpl(@NotNull List<T> encoded) {
        return encoded.stream().map(this::convert).collect(Collectors.toList());
    }

    @Contract("null -> null; !null -> !null")
    public final MV convert(Map<String, T> encoded) {
        return encoded != null ? convertImpl(encoded) : null;
    }

    abstract protected @NotNull MV convertImpl(@NotNull Map<String, T> encoded);
}
