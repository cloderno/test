import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // root direction
        String envRootDir = System.getProperty("user.dir");
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();
        System.out.format("RootDir: %s%n", rootDir);

        //args
        System.out.println("Кол-во аргументов: " + args.length);

        for(int i = 0; i < args.length; i++) {
            System.out.println("#################");
            System.out.println("\nФайл " + (i + 1) + ": " + args[i]);

            Charset charset = StandardCharsets.UTF_8;
            try (BufferedReader reader = Files.newBufferedReader(Path.of(args[i]), charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException x) {
                // File not exists exception might get triggered
                System.err.format("IOException: %s%n", x);
            }

            String s = "hello";
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of("output"), charset)) {
                writer.write(s, 0, s.length());
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }
}