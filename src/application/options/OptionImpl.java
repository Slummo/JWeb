package application.options;

public class OptionImpl<T extends OptionConfig> implements Option {
    protected final T config;

    public OptionImpl(T config) {
        this.config = config;
    }
}
