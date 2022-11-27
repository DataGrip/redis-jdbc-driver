package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectTypeField<T> {

    private final String name;
    private final SimpleType<?> simpleType;
    private final Function<T, ?> getter;
    private final Predicate<RedisQuery> isPresent;

    <S> ObjectTypeField(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull Function<T, S> getter,
                        @Nullable Predicate<RedisQuery> isPresent) {
        this.name = name;
        this.simpleType = simpleType;
        this.getter = getter;
        this.isPresent = isPresent;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull SimpleType<?> getSimpleType() {
        return simpleType;
    }

    public @NotNull Function<T, ?> getGetter() {
        return getter;
    }

    public boolean isPresent(@NotNull RedisQuery query) {
        return isPresent == null || isPresent.test(query);
    }
}
