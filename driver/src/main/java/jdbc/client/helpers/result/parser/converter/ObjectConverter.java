package jdbc.client.helpers.result.parser.converter;

import jdbc.client.structures.result.ObjectType;
import jdbc.client.structures.result.ObjectTypeField;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ObjectConverter<T> extends Converter<T, Map<String, Object>, List<Map<String, Object>>> {

    public abstract ObjectType<T> getObjectType();

    @Override
    protected final @NotNull Map<String, Object> convertImpl(@NotNull T encoded) {
        return getObjectType().stream().collect(Collectors.toMap(ObjectTypeField::getName, t -> t.getGetter().apply(encoded)));
    }

    protected final @NotNull Map<String, Object> convertEntryImpl(@NotNull Map.Entry<String, T> encoded) {
        Map<String, Object> converted = convertImpl(encoded.getValue());
        ObjectTypeField<T> mainField = getObjectType().getMainField();
        if (mainField != null) {
            converted.put(mainField.getName(), encoded.getKey());
        }
        return convertImpl(encoded.getValue());
    }

    @Override
    protected @NotNull List<Map<String, Object>> convertMapImpl(@NotNull Map<String, T> encoded) {
        return encoded.entrySet().stream().map(this::convertEntryImpl).collect(Collectors.toList());
    }
}
