package jdbc.client.helpers.result.parser.type;

import org.jetbrains.annotations.NotNull;

public class ObjectTypeElement {
    private final String name;
    private final String typeName;
    private final boolean isOptional;

    ObjectTypeElement(@NotNull String name, @NotNull String typeName, boolean isOptional) {
        this.name = name;
        this.typeName = typeName;
        this.isOptional = isOptional;
    }

    ObjectTypeElement(@NotNull String name, @NotNull String typeName) {
        this(name, typeName, false);
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getTypeName() {
        return typeName;
    }

    public boolean isOptional() {
        return isOptional;
    }
}
