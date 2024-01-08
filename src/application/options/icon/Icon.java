package application.options.icon;

import application.options.OptionImpl;
import application.options.Settable;

public class Icon extends OptionImpl<IconConfig> implements Settable {
    private boolean set;

    public Icon(IconConfig config) {
        super(config);
        set = false;
    }

    public String getPath() {
        return config.getPath();
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
