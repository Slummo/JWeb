package reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public interface AnnotationFinder<T extends AnnotatedElement> {
    default ArrayList<T> find(ArrayList<T> targets, Class<? extends Annotation> annotationClass) {
        var found = new ArrayList<T>();

        for (var t : targets) {
            if (t.isAnnotationPresent(annotationClass)) {
                found.add(t);
            }
        }

        return found;
    }

    default ArrayList<T> find(T[] targets, Class<? extends Annotation> annotationClass) {
        var arr = new ArrayList<>(List.of(targets));
        return find(arr, annotationClass);
    }
}
