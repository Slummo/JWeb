package utils;

import logger.Logger;
import utils.files.ExtensionFilter;
import utils.files.FS;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RF {
    /**
     * Parses a byte code file's absolute path to get the corresponding
     * package-qualified name
     * @param classFile The byte code file to be parsed
     * @param basePath The path where the first package is located
     * @return A package-qualified class file's name
     */
    private static String parseClassFile(File classFile, String basePath) {
        String filePath = classFile.getAbsolutePath();
        return filePath
            .substring(filePath.lastIndexOf(basePath) + basePath.length() + 1)
            .replaceAll(Pattern.quote(File.separator), ".")
            .replaceAll(".class", "");
    }

    /**
     * Gets a list of classes contained within a directory.
     * <p>This method directly uses {@link FS#getFilesInDir(Path, ExtensionFilter)}</p>
     *
     * @param dirPath The directory in which to search for classes.
     *                The method already appends to the start of this path a file separator,
     *                so providing it will have no effect
     * @param basePath The path where the first package is located
     * @return A list of classes contained within a directory
     *
     * @see FS
     */
    public static ArrayList<Class<?>> getClassesInDir(Path dirPath, Path basePath) {
        var files = FS.getFilesInDir(dirPath, new ExtensionFilter("class"));
        var classes = new ArrayList<Class<?>>();

        basePath = basePath.startsWith(File.separator) ? basePath : Path.of(File.separator + basePath);

        for (var f : files) {
            try {
                // Convert file path to package-qualified class name
                String className = parseClassFile(f, basePath.toString());
                
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                Logger.err(e);
            }
        }

        return classes;
    }

    /**
     * Overload method of {@link RF#getClassesInDir(Path, Path)}
     */
    public static ArrayList<Class<?>> getClassesInDir(String dirPath, String basePath) {
        return getClassesInDir(Path.of(dirPath), Path.of(basePath));
    }

    /**
     * Delegate method of {@link Class#getAnnotations()}
     *
     * @param c An instance of {@link Class}
     *
     * @return An {@link ArrayList<Annotation>} containing the annotations of c.
     *
     * @see Class
     */
    public static ArrayList<Annotation> getClassAnnotations(Class<?> c) {
        var annotations = new ArrayList<Annotation>();
        Collections.addAll(annotations, c.getAnnotations());

        return annotations;
    }

    /**
     * Delegate method of {@link Class#getMethods()} ()}
     *
     * @param c An instance of {@link Class}
     *
     * @return An {@link ArrayList<Method>} containing the methods of {@code c}.
     *
     * @see Class
     */
    public static ArrayList<Method> getClassMethods(Class<?> c) {
        var methods = new ArrayList<Method>();
        Collections.addAll(methods, c.getMethods());

        return methods;
    }

    /**
     * Gets the annotations of a method.
     *
     * @param method An instance of {@link Method}
     *
     * @return An {@link ArrayList<Annotation>} containing the annotations of {@code method}.
     *
     * @see Class
     */
    public static ArrayList<Annotation> getMethodAnnotations(Method method) {
        var annotations = new ArrayList<Annotation>();
        Collections.addAll(annotations, method.getAnnotations());

        return annotations;
    }

    public static HashMap<Method, ArrayList<Annotation>> getClassMethodsAnnotations(Class<?> c) {
        var map = new HashMap<Method, ArrayList<Annotation>>();
        var methods = getClassMethods(c);

        for (var m : methods) {
            map.put(m, getMethodAnnotations(m));
        }

        return map;
    }
}
