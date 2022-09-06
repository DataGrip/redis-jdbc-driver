package jdbc.client.helpers.result.parser.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class Converter<T, V> {

    public abstract V convert(T encoded);

    public final List<V> convert(List<T> encoded) {
        return encoded.stream().map(this::convert).collect(Collectors.toList());
    }

    public final Map<String, V> convert(Map<String, T> encoded) {
        return encoded.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convert(e.getValue())));
    }
}
