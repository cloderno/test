package helpers;

public class TypeChecker {
    public static Object parse(String input) {
        input = input.trim();
        if (input.isEmpty()) return input;

        try {
            if (input.contains(".")) {
                return Double.parseDouble(input);
            } else {
                return Long.parseLong(input);
            }
        } catch (NumberFormatException e) {
            return input;
        }
    }
}
