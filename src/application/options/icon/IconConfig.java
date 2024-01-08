package application.options.icon;

import application.options.OptionConfig;
import utils.files.FS;

public class IconConfig implements OptionConfig {
    private final String path;

    public IconConfig(String folderName) {
        path = FS.join(folderName, "favicon.ico");
    }

    public String getPath() {
        return path;
    }
}
