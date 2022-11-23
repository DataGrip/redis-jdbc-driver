package jdbc.client.helpers.result.parser.converter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ObjectConverter<T> extends Converter<T, Map<String, Object>, List<Map<String, Object>>> {

    protected @NotNull Map<String, Object> convertEntryImpl(@NotNull Map.Entry<String, T> encoded) {
        return convertImpl(encoded.getValue());
    }

    @Override
    protected @NotNull List<Map<String, Object>> convertImpl(@NotNull Map<String, T> encoded) {
        return encoded.entrySet().stream().map(this::convertEntryImpl).collect(Collectors.toList());
    }
}
