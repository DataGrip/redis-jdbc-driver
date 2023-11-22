package jdbc.client.query.structures;

import org.jetbrains.annotations.NotNull;

public class ColumnHint {

    private final String name;
    private final String[] values;

    public ColumnHint(@NotNull String name, @NotNull String[] values) {
        this.name = name;
        this.values = values;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String[] getValues() {
        return values;
    }
}
