package jdbc.client.helpers.result.parser.converter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Converter<T> {

    public abstract Map<String, Object> convert(T encoded);

    public final List<Map<String, Object>> convert(List<T> encoded) {
        return encoded.stream().map(this::convert).collect(Collectors.toList());
    }
}
