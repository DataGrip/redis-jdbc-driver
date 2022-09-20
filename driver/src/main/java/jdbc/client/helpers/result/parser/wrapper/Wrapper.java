package jdbc.client.helpers.result.parser.wrapper;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Wrapper {

    private Wrapper() {
    }

    @Contract("null -> null; !null -> !null")
    private static @Nullable Object wrap(@Nullable Object converted) {
        return JSONObject.wrap(converted);
    }

    public static @NotNull List<Object> wrapList(@NotNull List<?> converted) {
        return converted.stream().map(Wrapper::wrap).collect(Collectors.toList());
    }

    public static @NotNull Map<String, Object> wrapMap(@NotNull Map<String, ?> converted) {
        return converted.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> wrap(e.getValue())));
    }

    public static @NotNull List<Map<String, Object>> wrapObject(@NotNull List<Map<String, Object>> converted) {
        return converted.stream().map(Wrapper::wrapMap).collect(Collectors.toList());
    }
}
