package utils.files;

import logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Directory {
    private final Path dirPath;

    public Directory(Path dirPath) {
        this.dirPath = dirPath;
    }

    public Directory(String dirPath) {
        this(Path.of(dirPath));
    }

    public ArrayList<Path> getPaths(FS.PathType type) {
        var paths = new ArrayList<Path>();

        try (var stream = Files.newDirectoryStream(dirPath)) {
            for (Path p : stream) {
                switch (type) {
                    case FILE -> {
                        if (Files.isDirectory(p)) {
                            var dir = new Directory(p);
                            paths.addAll(dir.getPaths(type));
                        }
                        else if (Files.isRegularFile(p)) paths.add(p);
                    }
                    case DIRECTORY -> {
                        if (Files.isDirectory(p)) {
                            paths.add(p);
                            var dir = new Directory(p);
                            paths.addAll(dir.getPaths(type));
                        }
                    }
                    case BOTH -> {
                        if (Files.isDirectory(p)) {
                            paths.add(p);
                            var dir = new Directory(p);
                            paths.addAll(dir.getPaths(type));
                        } else if (Files.isRegularFile(p)) paths.add(p);
                    }
                }
            }
        } catch (IOException e) {
            Logger.err(e);
        }

        return paths;
    }

    public ArrayList<File> getFiles() {
        var paths = getPaths(FS.PathType.FILE);
        var files = new ArrayList<File>();

        for (var p : paths) {
            files.add(p.toFile());
        }

        return files;
    }

    public ArrayList<File> getFiles(ExtensionFilter filter) {
        var files = getFiles();
        var filtered = new ArrayList<File>();

        for (var f : files) {
            if (filter.accept(f)) {
                filtered.add(f);
            }
        }

        return filtered;
    }
}
