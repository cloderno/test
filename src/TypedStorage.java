import helpers.Validator;

import java.util.HashSet;
import java.util.Set;

public class TypedStorage {
    private Set<String> strings = new HashSet<>();
    private Set<Long> integers = new HashSet<>();
    private Set<Double> doubles = new HashSet<>();

    public void displayShort() {
        System.out.println("------------------------------");
        System.out.println("SHORT STATS:");
        System.out.println("strings count: " + strings.size());
        System.out.println("integers count: " + integers.size());
        System.out.println("doubles count: " + doubles.size());
        System.out.println("------------------------------\n");
    }

    /*
        Полная статистика для чисел
        дополнительно содержит минимальное и максимальное значения, сумма и среднее.

        Полная статистика для строк, помимо их количества, содержит также размер самой
        короткой строки и самой длинной.
    */
    public void displayFull() {
        System.out.println("\n------------------------------");
        System.out.println("SHORT STATS:");
        System.out.println("strings count: " + strings.size());
        System.out.println("integers count: " + integers.size());
        System.out.println("doubles count: " + doubles.size());
        System.out.println("------------------------------");
        System.out.println("------------------------------");
        System.out.println("STRING STATS:");
        System.out.println("Max String chars: " + maxChars());
        System.out.println("Min String chars: " + minChars());
        System.out.println("------------------------------\n");
        System.out.println("------------------------------");
        System.out.println("INT STATS:");
        System.out.println("Max INT chars: " );
        System.out.println("Min INT chars: " );
        System.out.println("Min SUM chars: " );
        System.out.println("Min AVG chars: " );
        System.out.println("------------------------------\n");
        System.out.println("------------------------------");
        System.out.println("Double STATS:");
        System.out.println("Max INT chars: " );
        System.out.println("Min INT chars: " );
        System.out.println("Min SUM chars: " );
        System.out.println("Min AVG chars: " );
        System.out.println("------------------------------\n");

    }

    public void add(Object o) {
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

    private <T> boolean isEmptySet(Set<T> set, String name) {
        if (!Validator.isSetPopulated(set)) {
            System.out.println("No content for " + name);
            return true;
        }
        return false;
    }

    private int maxChars() {
        if (isEmptySet(strings, "strings")) return 0;

        String maxLength = null;
        for (String s: strings) {
            if (maxLength == null || s.length() > maxLength.length()) {
                maxLength = s;
            }
        }

        return maxLength.length();
    }

    private int minChars() {
        if (isEmptySet(strings, "strings")) return 0;

        String minLength = null;
        for (String s: strings) {
            if (minLength == null || s.length() < minLength.length()) {
                minLength = s;
            }
        }

        return minLength.length();
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
