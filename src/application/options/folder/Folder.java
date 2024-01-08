package application.options.folder;

import application.options.OptionImpl;
import utils.files.FS;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Folder extends OptionImpl<FolderConfig> {
    public Folder(FolderConfig config) {
        super(config);
    }

    /**
     * Delegate method of {@link FolderConfig#getName()}
     * @see FolderConfig
     */
    public String getName() {
        return config.getName();
    }

    /**
     * Delegate method of {@link FolderConfig#getAbsolutePath()}
     * @see FolderConfig
     */
    public Path getAbsolutePath() {
        return config.getAbsolutePath();
    }

    /**
     * Delegate method of {@link FolderConfig#getFilesPaths()}
     * @see FolderConfig
     */
    public ArrayList<Path> getFilesPaths() {
        return config.getFilesPaths();
    }

    public String getRelativePath(File file) {
        return FS.getRelativePath(getAbsolutePath().toString(), file.getAbsolutePath());
    }

    public String getRelativePath(String path) {
        return getRelativePath(new File(path));
    }

    public String getRelativePath(Path path) {
        return getRelativePath(path.toString());
    }
}
