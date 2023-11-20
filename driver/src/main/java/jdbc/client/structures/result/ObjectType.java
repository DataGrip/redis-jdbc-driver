package jdbc.client.structures.result;

import jdbc.client.structures.query.RedisQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import static jdbc.client.helpers.result.parser.converter.TypeFactory.STRING;

public class ObjectType<T> extends ArrayList<ObjectTypeField<T>> {

    private final ObjectTypeField<T> mainField;

    public ObjectType(@NotNull String mainFieldName) {
        this.mainField = new ObjectTypeField<>(mainFieldName, STRING, e -> null, null);
        add(mainField);
    }

    public ObjectType() {
        this.mainField = null;
    }

    public <S> void add(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull Function<T, S> getter,
                        @Nullable Predicate<RedisQuery> isPresent) {
        add(new ObjectTypeField<>(name, simpleType, getter, isPresent));
    }

    public <S> void add(@NotNull String name,
                        @NotNull SimpleType<S> simpleType,
                        @NotNull Function<T, S> getter) {
        add(name, simpleType, getter, (Predicate<RedisQuery>) null);
    }

    public <S, R> void add(@NotNull String name,
                           @NotNull SimpleType<S> simpleType,
                           @NotNull Function<T, R> getter,
                           @NotNull Function<R, S> converter,
                           @Nullable Predicate<RedisQuery> isPresent) {
        add(name, simpleType, converter.compose(getter), isPresent);
    }

    public <S, R> void add(@NotNull String name,
                           @NotNull SimpleType<S> simpleType,
                           @NotNull Function<T, R> getter,
                           @NotNull Function<R, S> converter) {
        add(name, simpleType, getter, converter, null);
    }

    public @Nullable ObjectTypeField<T> getMainField() {
        return mainField;
    }
}
