package jdbc.client.result.parser.converter;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Converter<T, V, MV> {

    @Contract("null, _ -> null; !null, _ -> !null")
    public final V convert(T encoded, @NotNull Params params) {
        return encoded != null ? convertImpl(encoded, params) : null;
    }

    protected abstract @NotNull V convertImpl(@NotNull T encoded, @NotNull Params params);

    @Contract("null, _ -> null; !null, _ -> !null")
    public final List<V> convertList(List<T> encoded, @NotNull Params params) {
        return encoded != null ? convertListImpl(encoded, params) : null;
    }

    protected @NotNull List<V> convertListImpl(@NotNull List<T> encoded, @NotNull Params params) {
        return encoded.stream().map(e -> convert(e, params)).collect(Collectors.toList());
    }

    @Contract("null, _ -> null; !null, _ -> !null")
    public final MV convertMap(Map<String, T> encoded, @NotNull Params params) {
        return encoded != null ? convertMapImpl(encoded, params) : null;
    }

    abstract protected @NotNull MV convertMapImpl(@NotNull Map<String, T> encoded, @NotNull Params params);
}
