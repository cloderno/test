package helpers;

import java.util.Set;

/**
 * Утилитарный класс, который занимается очисткой/преобразованием строк
 */
public class StringFormatter {
    /**
     * Преобразует множество в строку, где каждый элемент — на новой строке.
     * Убирает квадратные скобки и запятые.
     */
    public static <T> String format(Set<T> data) {
        return data.toString().replace("[","").replace("]","").replace(", ", "\n");
    }
}
