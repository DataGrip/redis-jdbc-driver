package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ObjectType<T> extends ArrayList<ObjectTypeField> {
    public void add(@NotNull String name, @NotNull String typeName) {
        add(new ObjectTypeField(name, typeName));
    }

    public void add(@NotNull String name, @NotNull String typeName, @Nullable Predicate<RedisQuery> isPresent) {
        add(new ObjectTypeField(name, typeName, isPresent));
    }
}
