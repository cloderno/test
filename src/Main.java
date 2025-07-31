import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TypedStorage storage = new TypedStorage();

        var root = getUsersProjectRootDirectory() + "/output/";
        System.out.println("root: " + root);

        //args
        System.out.println("Кол-во аргументов: " + args.length);

        for(int i = 0; i < args.length; i++) {
            System.out.println("#################");
            System.out.println("\nФайл " + (i + 1) + ": " + args[i]);

            Charset charset = StandardCharsets.UTF_8;
            try (BufferedReader reader = Files.newBufferedReader(Path.of(args[i]), charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Object value = TypeChecker.parse(line);
                    storage.add(value);
                    System.out.println("Value: " + value + ", Type: " + value.getClass().getSimpleName());
                }
            } catch (IOException x) {
                // File not exists exception might get triggered
                System.err.format("IOException: %s%n", x);
            }

            String s = "hello";
            String filename = "output" + ".txt";
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(root + filename), charset)) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

            System.out.println("######################");
            System.out.println("STRINGS" + storage.getStrings());
            System.out.println("######################");

            System.out.println("######################");
            System.out.println("integers" + storage.getIntegers());
            System.out.println("######################");

            System.out.println("######################");
            System.out.println("doubles" + storage.getDoubles());
            System.out.println("######################");
        }
    }

    public static Path getUsersProjectRootDirectory() {
        String envRootDir = System.getProperty("user.dir");
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();
        if ( rootDir.startsWith(envRootDir) ) {
            return rootDir;
        } else {
            throw new RuntimeException("Root dir not found in user directory.");
        }
    }
}