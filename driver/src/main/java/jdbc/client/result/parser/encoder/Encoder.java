package jdbc.client.result.parser.encoder;

import jdbc.client.query.structures.Params;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Encoder<T> {
    public abstract @NotNull T encode(@Nullable Object data, @NotNull Params params);
}
