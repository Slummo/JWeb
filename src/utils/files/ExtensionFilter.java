package utils.files;

import java.io.File;
import java.io.FileFilter;

public class ExtensionFilter implements FileFilter {
    private String extension;

    /**
     * Creates a new {@link ExtensionFilter} from a given extension
     *
     * @param extension The extension name of the file without the dot
     */
    public ExtensionFilter(String extension) {
        this.extension = extension;
    }

    /**
     *
     * @param pathname The abstract pathname to be tested
     * @return A boolean value indicating whether the file has the correct extension
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getAbsolutePath().endsWith("." + extension);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
