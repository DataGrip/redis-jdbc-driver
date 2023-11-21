package jdbc.client.structures.query;

import jdbc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Protocol.Keyword;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Params {

    private final String[] params;
    private Set<String> paramNamesSet;

    public Params(@NotNull String[] params) {
        this.params = params;
    }

    public @NotNull String[] getRawParams() {
        return params;
    }

    public int getLength() {
        return params.length;
    }

    public boolean contains(@NotNull Keyword keyword) {
        if (paramNamesSet == null) {
            paramNamesSet = Arrays.stream(params).map(Utils::toUpperCase).collect(Collectors.toSet());
        }
        return paramNamesSet.contains(keyword.name());
    }
}
