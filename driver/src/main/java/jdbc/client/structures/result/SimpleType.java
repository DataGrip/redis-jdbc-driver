package jdbc.client.structures.result;

import org.jetbrains.annotations.NotNull;

public class SimpleType<T> {

    private final String typeName;

    public SimpleType(@NotNull String typeName) {
        this.typeName = typeName;
    }

    public @NotNull String getTypeName() {
        return typeName;
    }
}
