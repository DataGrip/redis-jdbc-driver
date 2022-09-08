package jdbc.client.structures.result;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedisListResult implements RedisResult {

    private final String type;
    private final List<Object> result;

    public RedisListResult(@NotNull String type, @NotNull List<Object> result) {
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
    public List<Object> getResult() {
        return result;
    }
}