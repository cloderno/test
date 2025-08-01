package helpers;

/**
 * Утилитарный класс который занимается проверкой типов и их преобразованием
 */
public class TypeChecker {
    /**
     * Метод очищает строку от пробелов, проверяет тип данных и преобразует в нужный
     * @param input принимает строку
     * @return возвращает нужный тип
     */
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
