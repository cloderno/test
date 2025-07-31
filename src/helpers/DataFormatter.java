package helpers;

import java.util.Set;
import java.util.stream.Collectors;

public class DataFormatter {
    public static <T> String format(Set<T> data) {
        return data.toString().replace("[","").replace("]","").replace(", ", "\n");
    }
}
