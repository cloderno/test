import java.util.HashSet;
import java.util.Set;

public class TypedStorage {
    private Set<String> strings = new HashSet<>();
    private Set<Long> integers = new HashSet<>();
    private Set<Double> doubles = new HashSet<>();

    public void displayShort() {
        System.out.println("\n------------------------------");
        System.out.println("strings count: " + strings.size());
        System.out.println("integers count: " + integers.size());
        System.out.println("doubles count: " + doubles.size());
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
