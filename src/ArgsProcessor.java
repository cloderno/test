import helpers.PathManager;

import java.util.HashSet;
import java.util.Set;

public class ArgsProcessor {
    private final String[] args;

    private boolean summaryShort = false;
    private boolean summaryFull = false;
    private String filePath = "";
    private String filePrefix = "";
    private Set<String> inputFiles = new HashSet<>(); //input/input1.txt

    public ArgsProcessor(String[] args) {
        this.args = args;

        processArgs();
    }

    public void processArgs() {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-s":
                    summaryShort = true;
                    continue;
                case "-f":
                    summaryFull = true;
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

    private void addInputFiles(String filename) {
        if (filename.endsWith(".txt")) {
            var filePath = PathManager.getRootDir() + "/src/" + filename;
            inputFiles.add(filePath);
        }
    }

    private boolean validateFilePath(String path) {
        if (PathManager.isValidPathString(path)) {
            this.filePath = path;
            return true;
        } else {
            System.err.format("Invalid path argument for -o: " + path);
            System.err.format("\nValid path must be: /example1/");
            return false;
        }
    }

    private boolean validateFilePrefix(String prefix) {
        if (FileManager.isValidFilePrefix(prefix)) {
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
}
