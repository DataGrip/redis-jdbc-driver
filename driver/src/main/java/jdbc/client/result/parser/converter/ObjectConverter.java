package jdbc.client.result.parser.converter;

import jdbc.client.query.structures.Params;
import jdbc.client.result.structures.ObjectType;
import jdbc.client.result.structures.ObjectTypeField;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ObjectConverter<T> extends Converter<T, Map<String, Object>, List<Map<String, Object>>> {

    public abstract ObjectType<T> getObjectType();

    @Override
    protected final @NotNull Map<String, Object> convertImpl(@NotNull T encoded, @NotNull Params params) {
        return getObjectType().getPresentFields(params)
                .map(t -> new AbstractMap.SimpleImmutableEntry<>(t.getName(), t.getConverter().apply(encoded, params)))
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected final @NotNull Map<String, Object> convertEntryImpl(@NotNull Map.Entry<String, T> encoded, @NotNull Params params) {
        Map<String, Object> converted = convertImpl(encoded.getValue(), params);
        ObjectTypeField<T> mainField = getObjectType().getMainField();
        if (mainField != null) {
            converted.put(mainField.getName(), encoded.getKey());
        }
        return converted;
    }

    @Override
    protected final @NotNull List<Map<String, Object>> convertMapImpl(@NotNull Map<String, T> encoded, @NotNull Params params) {
        return encoded.entrySet().stream().map(e -> convertEntryImpl(e, params)).collect(Collectors.toList());
    }
}
