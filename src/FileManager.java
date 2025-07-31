import helpers.DataFormatter;
import helpers.TypeChecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final String FILENAME_INTEGER = "result_integers.txt";
    private final String FILENAME_STRING = "result_strings.txt";
    private final String FILENAME_DOUBLE = "result_doubles.txt";

    private final TypedStorage storage;

    private final String[] args;
    private final String root;

    public FileManager(TypedStorage storage, String root, String[] args) {
        this.storage = storage;
        this.root = root;
        this.args = args;
    }

    public void processFile() {
        for (int i = 0; i < args.length; i++) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of(args[i]), DEFAULT_CHARSET)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Object value = TypeChecker.parse(line);
                    storage.add(value);
                }
            } catch (IOException x) {
                // Исключение отсутствия файла
                System.err.format("IOException: %s%n", x);
            }

            var strings = DataFormatter.format(storage.getStrings());
            this.writeFile(strings, FILENAME_STRING);

            var integers = DataFormatter.format(storage.getIntegers());
            this.writeFile(integers, FILENAME_INTEGER);

            var doubles = DataFormatter.format(storage.getDoubles());
            this.writeFile(doubles, FILENAME_DOUBLE);
        }
    }

    // content is already pre-cleaned
    private void writeFile(String content, String filename) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(root + filename), DEFAULT_CHARSET)) {
            writer.write(content, 0, content.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
