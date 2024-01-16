package utils;

import utils.files.Directory;
import utils.files.ExtensionFilter;
import utils.files.FS;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RF {
    private static String getJavaFilePackageName(File file) throws IOException {
        var reader = new BufferedReader(new FileReader(file));
        String packageName = reader.readLine()
                .replaceAll("package ", "")
                .replaceAll(Pattern.quote(File.separator), ".")
                .replace(";", "");
        String className = file.getName().replaceAll(".java", "");

        return packageName + "." + className;
    }

    /**
     * Gets a list of classes contained within a directory.
     * <p>This method directly uses {@link Directory#getFiles()}</p>
     *
     * @param dirPath  The directory in which to search for classes.
     *                 The method already appends to the start of this path a file separator,
     *                 so providing it will have no effect
     * @return A list of classes contained within a directory
     * @see FS
     */
    public static ArrayList<Class<?>> getClassesInDir(Path dirPath) {
        var dir = new Directory(dirPath);
        var files = dir.getFiles(new ExtensionFilter("java"));
        var classes = new ArrayList<Class<?>>();

        for (var f : files) {
            try {
                String qualifiedName = getJavaFilePackageName(f);
                classes.add(Class.forName(qualifiedName));
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        return classes;
    }

    /**
     * Overload method of {@link RF#getClassesInDir(Path)}
     */
    public static ArrayList<Class<?>> getClassesInDir(String dirPath) {
        return getClassesInDir(Path.of(dirPath));
    }
}
