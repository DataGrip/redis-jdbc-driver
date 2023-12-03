package jdbc.client.result.structures;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class ObjectTypeField<T> {

    private final String name;
    private final SimpleType<?> simpleType;
    private final BiFunction<T, Params, ?> converter;
    private final Predicate<Params> isPresent;

    <S> ObjectTypeField(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull BiFunction<T, Params, S> converter,
                        @Nullable Predicate<Params> isPresent) {
        this.name = name;
        this.simpleType = simpleType;
        this.converter = converter;
        this.isPresent = isPresent;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull SimpleType<?> getSimpleType() {
        return simpleType;
    }

    public @NotNull BiFunction<T, Params, ?> getConverter() {
        return converter;
    }

    public boolean isPresent(@NotNull Params params) {
        return isPresent == null || isPresent.test(params);
    }
}
