package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ObjectTypeField {
    private final String name;
    private final String typeName;
    private final Predicate<RedisQuery> isPresent;

    ObjectTypeField(@NotNull String name, @NotNull String typeName, @Nullable Predicate<RedisQuery> isPresent) {
        this.name = name;
        this.typeName = typeName;
        this.isPresent = isPresent;
    }

    ObjectTypeField(@NotNull String name, @NotNull String typeName) {
        this(name, typeName, null);
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getTypeName() {
        return typeName;
    }

    public boolean isPresent(@NotNull RedisQuery query) {
        return isPresent == null || isPresent.test(query);
    }
}
