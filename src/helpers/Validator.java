package helpers;

import java.util.Set;

public class Validator {
    public static <T> boolean isSetPopulated(Set<T> set) {
        return set != null && !set.isEmpty();
    }

    public static boolean isValidPathString(String input) {
        String pattern = "^/(?:[A-Za-z0-9_-]+/)+$";
        return input.matches(pattern);
    }

    public static boolean isValidFilePrefix(String filename) {
        String pattern = "^[A-Za-z0-9_-]+$";
        return filename.matches(pattern);
    }

    public static boolean isTxtFile(String filename) {
        return filename.endsWith(".txt");
    }
}
