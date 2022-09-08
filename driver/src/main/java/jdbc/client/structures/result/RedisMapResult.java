package jdbc.client.structures.result;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RedisMapResult implements RedisResult {

    private final String type;
    private final Map<String, Object> result;

    public RedisMapResult(@NotNull String type, @NotNull Map<String, Object> result) {
        this.type = type;
        this.result = result;
    }

    @NotNull
    @Override
    public String getType() {
        return type;
    }

    @NotNull
    @Override
    public Map<String, Object> getResult() {
        return result;
    }
}
