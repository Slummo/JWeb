package application.options.log;

import logger.Logger;
import application.options.OptionConfig;

public class LogConfig implements OptionConfig {
    public LogConfig(String folderName) {
        Logger.init(folderName, "application.log", 1, false);
    }

    public LogConfig(String folderName, String logFileName) {
        Logger.init(folderName, logFileName, 1, false);
    }

    public LogConfig(String folderName, String logFileName, int maxSize) {
        Logger.init(folderName, logFileName, maxSize, false);
    }

    public LogConfig(String folderName, boolean toConsole) {
        Logger.init(folderName, "application.log", 1, toConsole);
    }

    public LogConfig(String folderName, String logFileName, boolean toConsole) {
        Logger.init(folderName, logFileName, 1, toConsole);
    }

    public LogConfig(String folderName, int maxSize, boolean toConsole) {
        Logger.init(folderName, "application.log", maxSize, toConsole);
    }

    public LogConfig(String folderName, int maxSize) {
        Logger.init(folderName, "application.log", maxSize, false);
    }

    public LogConfig(String folderName, String logFileName, int maxSize, boolean toConsole) {
        Logger.init(folderName, logFileName, maxSize, toConsole);
    }
}
