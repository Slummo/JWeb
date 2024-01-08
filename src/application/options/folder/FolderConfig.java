package application.options.folder;

import application.options.OptionConfig;
import utils.files.FS;

import java.nio.file.Path;
import java.util.ArrayList;

public class FolderConfig implements OptionConfig {
    private final Path absolutePath;
    private final String name;
    private final ArrayList<Path> filesPaths;

    public FolderConfig(String path) {
        absolutePath = Path.of(path).toAbsolutePath();
        name = absolutePath.getFileName().toString();
        filesPaths = FS.getPathsInDir(path, FS.PathType.FILE);
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Path> getFilesPaths() {
        return filesPaths;
    }
}
