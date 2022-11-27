package jdbc.client.helpers.result.parser.converter;

import jdbc.client.structures.result.SimpleType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class SimpleConverter<T, V> extends Converter<T, V, Map<String, V>> {

    public abstract SimpleType<V> getSimpleType();

    @Override
    protected @NotNull Map<String, V> convertMapImpl(@NotNull Map<String, T> encoded) {
        return encoded.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertImpl(e.getValue())));
    }
}
