import helpers.PathManager;

public class Main {
    public static void main(String[] args) {
        TypedStorage storage = new TypedStorage();
        ArgsProcessor argsProcessor = new ArgsProcessor(args);

        FileManager fileManager = new FileManager(storage, argsProcessor);

        if (argsProcessor.isSummaryShort()) {
            storage.displayShort();
        }
//        fileManager.processFile();
    }
}