package jdbc.client.structures.query;

import org.jetbrains.annotations.NotNull;

public class ColumnHint {
    private final String name;
    private final String[] values;

    public ColumnHint(@NotNull String name, @NotNull String[] values) {
        this.name = name;
        this.values = values;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String[] getValues() {
        return values;
    }
}
