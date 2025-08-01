package helpers;

import java.util.Set;

/**
 * Утилитарный класс который занимается валидацией
 */
public class Validator {
    /**
     * Проверяет, содержит ли множество хотя бы один элемент.
     * @return boolean тип
     */
    public static <T> boolean isSetPopulated(Set<T> set) {
        return set != null && !set.isEmpty();
    }

    /**
     * Проверяет, соответствует ли строка формату валидного пути.
     * Ожидаемый формат: начинается с `/`, затем одна или более папок, разделённых `/`.
     * Пример валидного пути: /folder1/folder2/
     */
    public static boolean isValidPathString(String input) {
        String pattern = "^/(?:[A-Za-z0-9_-]+/)+$";
        return input.matches(pattern);
    }

    /**
     * Проверяет, соответствует ли строка формату с префиксом.
     * Ожидаемый формат: не имеет других символов помимо -_
     * Пример валидного префикса: file_prefix-01
     */
    public static boolean isValidFilePrefix(String filename) {
        String pattern = "^[A-Za-z0-9_-]+$";
        return filename.matches(pattern);
    }

    /**
     * Проверяет, содержит ли строка .txt формат
     */
    public static boolean isTxtFile(String filename) {
        return filename.endsWith(".txt");
    }
}
