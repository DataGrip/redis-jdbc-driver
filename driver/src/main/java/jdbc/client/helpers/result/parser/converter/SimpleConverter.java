package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class SimpleConverter<T> extends Converter<T, Object, Map<String, Object>> {
    @Override
    protected @NotNull Map<String, Object> convertImpl(@NotNull Map<String, T> encoded) {
        return encoded.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertImpl(e.getValue())));
    }
}
