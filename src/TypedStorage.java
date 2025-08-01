import helpers.StorageStatistics;
import helpers.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс предназначенный для хранения и изменения множеств разных типов
 */
public class TypedStorage {
    private Set<String> strings = new HashSet<>();
    private Set<Long> integers = new HashSet<>();
    private Set<Double> doubles = new HashSet<>();

    /**
     * Отображает краткую статистику: количество элементов каждого типа
     */
    public void displayShortStats() {
        StorageStatistics.displayShortStats(strings, integers, doubles);
    }

    /**
     * Отображает полную статистику:
     * - Для строк: количество, мин/макс длина
     * - Для чисел: количество, мин/макс значение, сумма, среднее
     */
    public void displayFullStats() {
        StorageStatistics.displayFullStats(strings, integers, doubles);
    }

    public void add(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot add null object");
        }

        if (o instanceof String) {
            strings.add((String) o);
        } else if (o instanceof Long) {
            integers.add((Long) o);
        } else if (o instanceof Double) {
            doubles.add((Double) o);
        } else {
            System.err.println("Unsupported type: " + o.getClass().getSimpleName() + " — " + o);
        }
    }

    public Set<String> getStrings() {
        return strings;
    }

    public Set<Long> getIntegers() {
        return integers;
    }

    public Set<Double> getDoubles() {
        return doubles;
    }
}


