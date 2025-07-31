package helpers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathManager {
    public static boolean isValidPathString(String input) {
        String pattern = "^/(?:[A-Za-z0-9_-]+/)+$";
        return input.matches(pattern);
    }

    public static Path getRootDir() {
        String envRootDir = System.getProperty("user.dir");
        Path rootDir = Paths.get(".").normalize().toAbsolutePath();

        if (rootDir.startsWith(envRootDir) ) {
            return rootDir;
        } else {
            throw new RuntimeException("Root dir not found in user directory.");
        }
    }
}
