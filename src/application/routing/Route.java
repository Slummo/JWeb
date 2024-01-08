package application.routing;

import application.http.structure.request.RequestMethod;
import application.routing.config.EndpointMapper;
import reflection.AnnotationFinder;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Route implements AnnotationFinder<Method> {
    private final String path;
    private final ArrayList<Endpoint> endPoints;

    public Route(Class<?> routeClass) {
        path = File.separator + routeClass.getPackageName().replace('.', File.separatorChar);

        endPoints = new ArrayList<>();

        var methods = routeClass.getMethods();
        var valid = find(methods, EndpointMapper.class);
        for (var m : valid) endPoints.add(new Endpoint(m));
    }

    public Route(String path, OptionHandler handler, RequestMethod requestMethod) throws NoSuchMethodException {
        this.path = path;

        endPoints = new ArrayList<>();
        endPoints.add(new Endpoint(handler, requestMethod));
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Endpoint> getEndPoints() {
        return endPoints;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "path='" + path + '\'' +
                ", endPointsInfo=" + endPoints +
                '}';
    }
}
