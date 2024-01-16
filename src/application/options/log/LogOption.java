package application.options.log;

import application.options.OptionImpl;
import application.options.Settable;

public class LogOption extends OptionImpl<LogConfig> implements Settable {
    private boolean set;

    public LogOption(LogConfig config) {
        super(config);
        set = false;
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
