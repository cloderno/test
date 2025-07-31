package helpers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathManager {
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
