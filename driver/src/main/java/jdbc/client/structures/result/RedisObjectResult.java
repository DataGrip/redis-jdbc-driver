package jdbc.client.structures.result;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class RedisObjectResult implements RedisResult {

    private final Map<String, String> type;
    private final List<Map<String, Object>> result;

    public RedisObjectResult(@NotNull Map<String, String> type, @NotNull List<Map<String, Object>> result) {
        this.type = type;
        this.result = result;
    }

    @NotNull
    @Override
    public Map<String, String> getType() {
        return type;
    }

    @NotNull
    @Override
    public List<Map<String, Object>> getResult() {
        return result;
    }
}

