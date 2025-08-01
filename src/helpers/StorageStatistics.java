package helpers;

import java.util.Set;

/**
 * Утилитарный класс для вычисления и отображения статистики коллекций
 */
public class StorageStatistics {
    private static final String SEPARATOR = "------------------------------";

    private StorageStatistics() { }

    /**
     * Выводит краткую статистику — количество элементов в каждом из переданных множеств:
     * @param strings множество строк
     * @param integers множество целых чисел (Long)
     * @param doubles множество чисел с плавающей точкой (Double)
     */
    public static void displayShortStats(Set<String> strings, Set<Long> integers, Set<Double> doubles) {
        System.out.println(SEPARATOR);
        System.out.println("SHORT STATS:");
        System.out.println("strings count: " + strings.size());
        System.out.println("integers count: " + integers.size());
        System.out.println("doubles count: " + doubles.size());
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Выводит полную статистику по каждому из множеств
     * Выполняет операции min, max, sum и avg
     */
    public static void displayFullStats(Set<String> strings, Set<Long> integers, Set<Double> doubles) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("FULL STATS:");
        System.out.println("strings count: " + strings.size());
        System.out.println("integers count: " + integers.size());
        System.out.println("doubles count: " + doubles.size());
        System.out.println(SEPARATOR);

        displayStringStats(strings);
        displayIntegerStats(integers);
        displayDoubleStats(doubles);
    }

    /**
     * Выводит статистику по строкам
     */
    private static void displayStringStats(Set<String> strings) {
        System.out.println(SEPARATOR);
        System.out.println("STRING STATS:");

        if (isEmptySet(strings, "strings")) {
            System.out.println(SEPARATOR + "\n");
            return;
        }

        System.out.println("Max String chars: " + getMaxStringLength(strings));
        System.out.println("Min String chars: " + getMinStringLength(strings));
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Выводит статистику по числам
     */
    private static void displayIntegerStats(Set<Long> integers) {
        System.out.println(SEPARATOR);
        System.out.println("INTEGER STATS:");

        if (isEmptySet(integers, "integers")) {
            System.out.println(SEPARATOR + "\n");
            return;
        }

        System.out.println("Max INT: " + getMaxInteger(integers));
        System.out.println("Min INT: " + getMinInteger(integers));
        System.out.println("SUM INT: " + getIntegerSum(integers));
        System.out.println("AVG INT: " + getIntegerAverage(integers));
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Выводит статистику по вещественным
     */
    private static void displayDoubleStats(Set<Double> doubles) {
        System.out.println(SEPARATOR);
        System.out.println("DOUBLE STATS:");

        if (isEmptySet(doubles, "doubles")) {
            System.out.println(SEPARATOR + "\n");
            return;
        }

        System.out.println("Max Double: " + getMaxDouble(doubles));
        System.out.println("Min Double: " + getMinDouble(doubles));
        System.out.println("SUM Double: " + getDoubleSum(doubles));
        System.out.println("AVG Double: " + getDoubleAverage(doubles));
        System.out.println(SEPARATOR + "\n");
    }

    /**
     * Проверка на содержание
     * @param set множество
     * @param name название множества
     * @return возвращает boolean
     */
    private static <T> boolean isEmptySet(Set<T> set, String name) {
        if (!Validator.isSetPopulated(set)) {
            System.out.println("No content for " + name);
            return true;
        }
        return false;
    }

    /**
     * Получение максимальной строки
     */
    public static int getMaxStringLength(Set<String> strings) {
        if (strings.isEmpty()) return 0;

        int maxLength = 0;
        for (String s : strings) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        return maxLength;
    }

    /**
     * Получение минимальной строки
     */
    public static int getMinStringLength(Set<String> strings) {
        if (strings.isEmpty()) return 0;

        Integer minLength = null;
        for (String s : strings) {
            if (minLength == null || s.length() < minLength) {
                minLength = s.length();
            }
        }
        return minLength;
    }

    /**
     * Получение максимального числа
     */
    public static Long getMaxInteger(Set<Long> integers) {
        if (integers.isEmpty()) return 0L;

        Long max = null;
        for (Long i : integers) {
            if (max == null || i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * Получение минимального числа
     */
    public static Long getMinInteger(Set<Long> integers) {
        if (integers.isEmpty()) return 0L;

        Long min = null;
        for (Long i : integers) {
            if (min == null || i < min) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Получение суммированного числа
     */
    public static Long getIntegerSum(Set<Long> integers) {
        if (integers.isEmpty()) return 0L;

        Long sum = 0L;
        for (Long i : integers) {
            sum += i;
        }
        return sum;
    }

    /**
     * Получение среднего арифметического числа
     */
    public static Double getIntegerAverage(Set<Long> integers) {
        if (integers.isEmpty()) return 0.0;

        Long sum = getIntegerSum(integers);
        return sum.doubleValue() / integers.size();
    }

    /**
     * Получение максимального числа типа Double
     */
    public static Double getMaxDouble(Set<Double> doubles) {
        if (doubles.isEmpty()) return 0.0;

        Double max = null;
        for (Double d : doubles) {
            if (max == null || d > max) {
                max = d;
            }
        }
        return max;
    }

    /**
     * Получение минимального числа типа Double
     */
    public static Double getMinDouble(Set<Double> doubles) {
        if (doubles.isEmpty()) return 0.0;

        Double min = null;
        for (Double d : doubles) {
            if (min == null || d < min) {
                min = d;
            }
        }
        return min;
    }

    /**
     * Получение суммирование типов Double
     */
    public static Double getDoubleSum(Set<Double> doubles) {
        if (doubles.isEmpty()) return 0.0;

        Double sum = 0.0;
        for (Double d : doubles) {
            sum += d;
        }
        return sum;
    }

    /**
     * Получение среднего арифметического числа типа Double
     */
    public static Double getDoubleAverage(Set<Double> doubles) {
        if (doubles.isEmpty()) return 0.0;

        Double sum = getDoubleSum(doubles);
        return sum / doubles.size();
    }
}