package jdbc.client.query.structures;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.args.Rawable;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static jdbc.utils.Utils.getName;

public class Params {

    private final String[] params;
    private Map<String, Integer> paramNames;

    public Params(@NotNull String[] params) {
        this.params = params;
    }

    public @NotNull String[] getRawParams() {
        return params;
    }

    public int getLength() {
        return params.length;
    }

    public @Nullable String getFirst() {
        return params.length > 0 ? params[0] : null;
    }

    public @Nullable String getNext(@NotNull Rawable keyword) {
        Integer index = getParamNames().get(getName(keyword));
        int nextIndex = index != null ? index + 1 : params.length;
        return nextIndex < params.length ? params[nextIndex] : null;
    }

    public boolean contains(@NotNull Rawable keyword) {
        return getParamNames().containsKey(getName(keyword));
    }

    private @NotNull Map<String, Integer> getParamNames() {
        if (paramNames == null) {
            Stream<Integer> paramIndexes = IntStream.range(0, params.length).boxed();
            paramNames = paramIndexes.collect(Collectors.toMap(i -> getName(params[i]), i -> i, (n1, n2) -> n1));
        }
        return paramNames;
    }
}
