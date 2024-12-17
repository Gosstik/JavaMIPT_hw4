package mipt.hw.components.correctors;

import java.util.Objects;

public class NameCorrector {

    public static String correctName(String name) {
        Objects.requireNonNull(name);
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
