package jdbc.client.result.structures;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static jdbc.client.result.parser.converter.TypeFactory.STRING;

public class ObjectType<T> extends ArrayList<ObjectTypeField<T>> {

    private final ObjectTypeField<T> mainField;

    public ObjectType(@NotNull String mainFieldName) {
        this.mainField = new ObjectTypeField<>(mainFieldName, STRING, (e, p) -> null, null);
        add(mainField);
    }

    public ObjectType() {
        this.mainField = null;
    }

    private <S> void add(@NotNull String name,
                         @NotNull SimpleType<S> simpleType,
                         @NotNull BiFunction<T, Params, S> converter,
                         @Nullable Predicate<Params> isPresent) {
        add(new ObjectTypeField<T>(name, simpleType, converter, isPresent));
    }

    public <S> void add(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull Function<T, S> getter,
                        @Nullable Predicate<Params> isPresent) {
        add(name, simpleType, (e, p) -> getter.apply(e), isPresent);
    }

    public <S> void add(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull BiFunction<T, Params, S> converter) {
        add(name, simpleType, converter, null);
    }

    public <S> void add(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull Function<T, S> getter) {
        add(name, simpleType, getter, (Predicate<Params>) null);
    }

    public <S, R> void add(@NotNull String name,
                           @NotNull SimpleType<S> simpleType,
                           @NotNull Function<T, R> getter,
                           @NotNull BiFunction<R, Params, S> converter) {
        add(name, simpleType, getter, converter, null);
    }

    public <S, R> void add(@NotNull String name,
                           @NotNull SimpleType<S> simpleType,
                           @NotNull Function<T, R> getter,
                           @NotNull BiFunction<R, Params, S> converter,
                           @Nullable Predicate<Params> isPresent) {
        add(name, simpleType, (e, p) -> converter.apply(getter.apply(e), p), isPresent);
    }

    public @Nullable ObjectTypeField<T> getMainField() {
        return mainField;
    }

    public @NotNull Stream<ObjectTypeField<T>> getPresentFields(@NotNull Params params) {
        return stream().filter(e -> e.isPresent(params));
    }
}
