package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Converter<T, V, MV> {

    @Contract("null -> null; !null -> !null")
    public final V convert(T encoded) {
        return encoded != null ? convertImpl(encoded) : null;
    }

    protected abstract @NotNull V convertImpl(@NotNull T encoded);

    @Contract("null -> null; !null -> !null")
    public final List<V> convertList(List<T> encoded) {
        return encoded != null ? convertListImpl(encoded) : null;
    }

    protected @NotNull List<V> convertListImpl(@NotNull List<T> encoded) {
        return encoded.stream().map(this::convert).collect(Collectors.toList());
    }

    @Contract("null -> null; !null -> !null")
    public final MV convertMap(Map<String, T> encoded) {
        return encoded != null ? convertMapImpl(encoded) : null;
    }

    abstract protected @NotNull MV convertMapImpl(@NotNull Map<String, T> encoded);
}
