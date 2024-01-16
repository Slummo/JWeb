package utils.files;

import logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Pattern;

public class FS {
    public enum PathType {
        FILE, DIRECTORY, BOTH
    }

    public static String join(String... paths) {
        var dir = new Directory(System.getProperty("user.dir"));
        var ps = dir.getPaths(PathType.BOTH);

        for (Path p : ps) {
            String s = p.toString();

            String last = paths[paths.length - 1];
            if (!s.endsWith(last)) continue;

            boolean containsAll = true;
            for (int i = 0; i < paths.length - 1; i++) {
                if (!s.contains(paths[i])) {
                    containsAll = false;
                    break;
                }
            }

            if (containsAll) return s.replaceAll(Pattern.quote(File.separator), "/");
        }

        return null;
    }

    public static String getRelativePath(String dirPath, String otherPath) throws InvalidPathException {
        var mainPath = Path.of(dirPath);
        if (!Files.isDirectory(mainPath)) throw new InvalidPathException(dirPath, "Not a directory");

        var dir = new Directory(dirPath);
        var paths = dir.getPaths(PathType.BOTH);
        for (Path p : paths) {
            String str = p.toString();
            if (str.contains(otherPath)) {
                String fileName = mainPath.getFileName().toString();
                return str.split(fileName)[1].replaceAll(Pattern.quote(File.separator), "/");
            }
        }

        return null;
    }

    public static Path getRelativePath(Path dirPath, Path otherPath) throws InvalidPathException {
        String s = getRelativePath(dirPath.toString(), otherPath.toString());
        if (s == null) return null;
        else return Path.of(s);
    }

    public static String getFileExtension(String fileName) throws IOException {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) throw new IOException("Exception occurred while reading the extension from '" + fileName + "'.");
        return fileName.substring(dotIndex + 1);
    }

    public static File createFileInFolder(String folderName, String fileName) throws IOException {
        Path folder = Paths.get(folderName);

        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        Path file = folder.resolve(fileName);

        if (!Files.exists(file)) {
            Files.createFile(file);
        }

        return file.toFile();
    }

    public static void cleanFileContent(String path) {
        try {
            Files.write(Path.of(path), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Logger.err(e);
        }
    }
}
