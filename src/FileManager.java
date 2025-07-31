import helpers.DataFormatter;
import helpers.PathManager;
import helpers.TypeChecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final String FILENAME_INTEGER = "integers.txt";
    private final String FILENAME_STRING = "strings.txt";
    private final String FILENAME_DOUBLE = "doubles.txt";

    private final TypedStorage storage;
    private final ArgsProcessor argsProcessor;

    public FileManager(TypedStorage storage, ArgsProcessor argsProcessor) {
        this.storage = storage;
        this.argsProcessor = argsProcessor;

        processFile();
    }

    public void processFile() {
        var inputFiles = argsProcessor.getInputFiles();

        for (var file : inputFiles) {
            try (BufferedReader reader = Files.newBufferedReader(Path.of(file), DEFAULT_CHARSET)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Object value = TypeChecker.parse(line);
                    storage.add(value);
                }
            } catch (IOException x) {
                // Исключение отсутствия файла
                System.err.format("IOException: %s%n", x);
                System.err.format("File not found");
            }

            write();
        }
    }

    private void write() {
        String strings = DataFormatter.format(storage.getStrings());
        this.insert(strings, FILENAME_STRING);

        String integers = DataFormatter.format(storage.getIntegers());
        this.insert(integers, FILENAME_INTEGER);

        String doubles = DataFormatter.format(storage.getDoubles());
        this.insert(doubles, FILENAME_DOUBLE);
    }

    private void insert(String content, String filename) {
        if (content == null || content.isBlank()) return;

        String baseDir = PathManager.getRootDir().toString();
        String subDir = argsProcessor.getFilePath();
        String directory = subDir.isEmpty() ? baseDir + "/output/" : baseDir + "/" + subDir;

        // directory check
        File outputDir = new File(directory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String fullFilename = argsProcessor.getFilePrefix() + filename;
        Path outputPath = Path.of(directory, fullFilename);

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, DEFAULT_CHARSET)) {
            writer.write(content, 0, content.length());
        } catch (IOException x) {
            System.err.format("IOException while writing to %s: %s%n", outputPath, x.getMessage());
        }
    }

    public static boolean isValidFilePrefix(String filename) {
        String pattern = "^[A-Za-z0-9_-]+$";
        return filename.matches(pattern);
    }
}
