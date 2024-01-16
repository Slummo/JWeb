package application.options.icon;

import application.options.OptionImpl;
import application.options.Settable;

import java.nio.file.Path;

public class IconOption extends OptionImpl<IconConfig> implements Settable {
    private boolean set;

    public IconOption(IconConfig config) {
        super(config);
        set = false;
    }

    public Path getPath() {
        return Path.of(config.getPath());
    }

    @Override
    public void set() {
        set = true;
    }

    @Override
    public boolean isSet() {
        return set;
    }
}
