import helpers.PathManager;

public class Main {
    public static void main(String[] args) {
        TypedStorage storage = new TypedStorage();
        ArgsProcessor argsProcessor = new ArgsProcessor(args);

        FileManager fileManager = new FileManager(storage, argsProcessor);
        fileManager.processFile();

        if (argsProcessor.isSummaryShort()) {
            storage.displayShortStats();
        }
        if (argsProcessor.isSummaryFull()) {
            storage.displayFullStats();
        }
    }
}