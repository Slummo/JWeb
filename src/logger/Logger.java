package logger;

import utils.files.FS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Logger {
    /**
     * Boolean value to indicate whether to print also to console or not.
     */
    private static boolean toConsole;
    /**
     * The {@link PrintStream} to write to the file.
     */
    private static PrintStream out;
    /**
     * Max size of the log file in MB.
     */
    private static int maxSize;

    /**
     * Static initializer function to create a new {@link Logger}.
     *
     * @param folderName The folder where the log file will be stored.
     * @param logFileName The custom name of the log file.
     * @param maxSize The max size of the log file. After this size is reached, the fill will be completely cleaned.
     * @param toConsole A boolean value to indicate whether to print also to console or not.
     */
    public static void init(String folderName, String logFileName, int maxSize, boolean toConsole) {
        Logger.toConsole = toConsole;
        Logger.maxSize = maxSize;
        try {
            File f = FS.createFileInFolder(folderName, logFileName);
            out = new PrintStream(new FileOutputStream(f, true));
        } catch (IOException e) {
            Logger.err(e);
        }
    }

    private static String getCurrentTime() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
    }

    private static Method getCallingMethod() {
        var stackTrace = Thread.currentThread().getStackTrace();
        try {
            // Index 2 corresponds to the calling method in the stack trace
            var c = Class.forName(stackTrace[2].getClassName());
            String methodName = stackTrace[2].getMethodName();

            for (Method method : c.getDeclaredMethods()) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
        } catch (ClassNotFoundException e) {
            Logger.err(e);
        }
        return null;
    }

    private static String getLogString(String msg, LogType type, NewLinePosition position, int newLineCharsCount) {
        String base = "[" + getCurrentTime() + "] {" + type + "} " + msg;
        String n = "\n".repeat(newLineCharsCount);
        switch (position) {
            case BEFORE -> {return n + base;}
            case AFTER -> {return base + n;}
            case BOTH -> {return n + base + n;}
            default -> {return base;}
        }
    }

    private static void log(String msg, LogType type, NewLinePosition position, int newLineCharsCount) {
        String logString = getLogString(msg, type, position, newLineCharsCount);
        boolean consoleErr = type.equals(LogType.ERR);
        out.println(logString);
        if (toConsole) {
            if (consoleErr) System.err.println(logString);
            else System.out.println(logString);
        };
    }

    private static void log(String msg, LogType type, Method method) {
        var logConfig = method.getAnnotation(LogConfig.class);
        var position = (logConfig != null) ? logConfig.position() : NewLinePosition.NONE;
        int newLineCharsCount = (logConfig != null) ? logConfig.newLineCharsCount() : 0;

        log(msg, type, position, newLineCharsCount);
    }

    private static void log(String msg, LogType type) {
        log(msg, type, Objects.requireNonNull(getCallingMethod()));
    }

    public static void info(String msg) {
        log(msg, LogType.INFO);
    }

    public static void warn(String msg) {
        log(msg, LogType.WARN);
    }

    public static void err(String msg) {
        log(msg, LogType.ERR);
    }

    public static void err(Exception e) {
        log(e.getMessage(), LogType.ERR);
    }

    public static boolean isToConsole() {
        return toConsole;
    }

    public static void setToConsole(boolean toConsole) {
        Logger.toConsole = toConsole;
    }

    public static int getMaxSize() {
        return maxSize;
    }

    public static void setMaxSize(int maxSize) {
        Logger.maxSize = maxSize;
    }
}
