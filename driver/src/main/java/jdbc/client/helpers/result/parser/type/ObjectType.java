package jdbc.client.helpers.result.parser.type;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ObjectType<T> extends ArrayList<ObjectTypeElement> {
    public void add(@NotNull String name, @NotNull String typeName) {
        add(new ObjectTypeElement(name, typeName));
    }

    public void add(@NotNull String name, @NotNull String typeName, boolean isOptional) {
        add(new ObjectTypeElement(name, typeName, isOptional));
    }
}
