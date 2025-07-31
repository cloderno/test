import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) {
        TypedStorage storage = new TypedStorage();
        var root = getUsersProjectRootDirectory() + "/output/";

        System.out.println("root: " + root);

        //args
        System.out.println("Кол-во аргументов: " + args.length);

        FileManager fileManager = new FileManager(storage, root, args);
        fileManager.processFile();
    }

    public static Path getUsersProjectRootDirectory() {
        String envRootDir = System.getProperty("user.dir");
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();

        if (rootDir.startsWith(envRootDir) ) {
            return rootDir;
        } else {
            throw new RuntimeException("Root dir not found in user directory.");
        }
    }
}