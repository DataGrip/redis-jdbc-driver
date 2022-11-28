package jdbc.client.helpers.result.parser.encoder;

import org.jetbrains.annotations.NotNull;

public abstract class Encoder<T> {
    public abstract @NotNull T encode(Object data);
}
