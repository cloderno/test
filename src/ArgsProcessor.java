import helpers.PathManager;
import helpers.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс занимающийся обработкой аргументов из CLI
 */
public class ArgsProcessor {
    private final String[] args;

    private boolean summaryShort = false;
    private boolean summaryFull = false;
    private boolean appendOption = false;
    private String filePath = "";
    private String filePrefix = "";
    private Set<String> inputFiles = new HashSet<>(); //input/input1.txt

    public ArgsProcessor(String[] args) {
        this.args = args;

        processArgs();
    }

    /**
     * Метод проверяет аргументы, находит в них опции и манипулирует полями
     */
    public void processArgs() {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    summaryShort = true;
                    continue;
                case "-f":
                    summaryFull = true;
                    continue;
                case "-a":
                    appendOption = true;
                    continue;
                case "-o":
                    if (!validateFilePath(args[++i])) break;
                    continue;
                case "-p":
                    if (!validateFilePrefix(args[++i])) break;
                    continue;
                default:
                    addInputFiles(args[i]);
                    break;
            }
        }
    }

    /**
     * Добавляет входной файл в inputFiles, если файл имеет расширение .txt.
     * Путь строится относительно директории /src/.
     *
     * @param filename имя входного файла (например, input/input1.txt)
     */
    private void addInputFiles(String filename) {
        if (Validator.isTxtFile(filename)) {
            var filePath = PathManager.getRootDir() + "/src/" + filename;
            inputFiles.add(filePath);
        }
    }

    /**
     * Валидирует путь, переданный в аргументе -o.
     * Путь должен начинаться и заканчиваться слешем, например: /example1/
     *
     * @param path путь указанный пользователем
     * @return true, если путь валиден, иначе false
     */
    private boolean validateFilePath(String path) {
        if (Validator.isValidPathString(path)) {
            this.filePath = path;
            return true;
        } else {
            System.err.format("Invalid path argument for -o: " + path);
            System.err.format("\nValid path must be: /example1/");
            return false;
        }
    }

    /**
     * Валидирует префикс имени файла, переданный в аргументе -p.
     * Разрешены только латинские буквы, цифры, символы '-' и '_'.
     *
     * @param prefix префикс имени файла
     * @return true, если префикс валиден, иначе false
     */
    private boolean validateFilePrefix(String prefix) {
        if (Validator.isValidFilePrefix(prefix)) {
            this.filePrefix = prefix;
            return true;
        } else {
            System.err.format("Invalid file prefix for -p: " + prefix);
            System.err.format("\nPrefix must not contain any other symbols than \"_-\"");
            System.err.format("\nValid prefix must be: [example_1, example, example-1]");
            return false;
        }
    }

    public Set<String> getInputFiles() {
        return inputFiles;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isSummaryFull() {
        return summaryFull;
    }

    public boolean isSummaryShort() {
        return summaryShort;
    }

    public boolean isAppendOption() {
        return appendOption;
    }
}
