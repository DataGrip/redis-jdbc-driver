package jdbc.client.structures.result;

import java.util.List;

public class RedisSimpleResult implements RedisResult {

    private final List<Object> result;
    private final String type;

    public RedisSimpleResult(List<Object> result, String type) {
        this.result = result;
        this.type = type;
    }

    @Override
    public List<Object> getResult() {
        return result;
    }

    @Override
    public String getType() {
        return type;
    }
}