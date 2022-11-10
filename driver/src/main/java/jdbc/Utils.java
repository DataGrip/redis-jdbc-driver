package jdbc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class Utils {

    private Utils() {
    }


    public static String toLowerCase(String value) {
        return value.toLowerCase(Locale.ENGLISH);
    }

    public static String toUpperCase(String value) {
        return value.toUpperCase(Locale.ENGLISH);
    }


    public static @Nullable String getFirst(@NotNull String[] elements) {
        return elements.length > 0 ? elements[0] : null;
    }

    public static @Nullable String getLast(@NotNull String[] elements) {
        return elements.length > 0 ? elements[elements.length - 1] : null;
    }


    public static String getString(Map<?, ?> map, String name, String defaultValue) {
        return getObject(map, name, defaultValue, Object::toString);
    }

    public static int getInt(Map<?, ?> map, String name, int defaultValue) {
        return getObject(map, name, defaultValue, Integer::parseInt);
    }

    private static <T> T getObject(Map<?, ?> map, String name, T defaultValue, Function<String, T> valueGetter) {
        if (map != null) {
            Object option = map.get(name);
            if (option != null) return valueGetter.apply(option.toString());
        }
        return defaultValue;
    }
}
