import helpers.StringFormatter;
import helpers.PathManager;
import helpers.TypeChecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileManager {
    // Стандартная кодировка для чтения/записи файлов
    private final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    // Названия выходных файлов по типам данных
    private final String FILENAME_INTEGER = "integers.txt";
    private final String FILENAME_STRING = "strings.txt";
    private final String FILENAME_DOUBLE = "doubles.txt";

    private final TypedStorage storage;
    private final ArgsProcessor argsProcessor;

    public FileManager(TypedStorage storage, ArgsProcessor argsProcessor) {
        this.storage = storage;
        this.argsProcessor = argsProcessor;
    }

    /**
     * Обрабатывает входные файлы, проверяет тип данных и записывает в каждую из множеств
     */
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
        }

        write();
    }

    /**
     * Записывает строки, целые числа и дробные числа в отдельные файлы, используя форматированный вывод и префиксы имен
     */
    private void write() {
        String strings = StringFormatter.format(storage.getStrings());
        this.createAndPopulate(strings, FILENAME_STRING);

        String integers = StringFormatter.format(storage.getIntegers());
        this.createAndPopulate(integers, FILENAME_INTEGER);

        String doubles = StringFormatter.format(storage.getDoubles());
        this.createAndPopulate(doubles, FILENAME_DOUBLE);
    }

    /**
     * Создает файл с заданным содержимым и именем
     * Учитывает путь (из опции -o), режим append (-a) и префикс файла (-p)
     * @param content  Содержимое
     * @param filename Имя файла без префикса
     */
    private void createAndPopulate(String content, String filename) {
        if (content == null || content.isBlank()) return;

        String baseDir = PathManager.getRootDir().toString(); // главная директория
        String subDir = argsProcessor.getFilePath(); // дополнительная директория полученная из опции
        String directory = subDir.isEmpty() ? baseDir + "/output/" : baseDir + "/" + subDir; // дефолтная или subdir

        // Проверка путей, на содержание папок
        File outputDir = new File(directory);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String fullFilename = argsProcessor.getFilePrefix() + filename; // префикс + файл
        Path outputPath = Path.of(directory, fullFilename); // окончательная директория + имя файла

        // Проверка опций
        OpenOption[] options = argsProcessor.isAppendOption()
                ? new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.APPEND }
                : new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, DEFAULT_CHARSET, options)) {
            if (argsProcessor.isAppendOption() && Files.exists(outputPath) && Files.size(outputPath) > 0) {
                writer.newLine();
            }
            writer.write(content, 0, content.length());
        } catch (IOException x) {
            System.err.format("IOException while writing to %s: %s%n", outputPath, x.getMessage());
        }
    }
}
