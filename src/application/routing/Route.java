package application.routing;

import application.http.structure.request.RequestMethod;
import application.options.OptionHandler;
import application.routing.config.EndpointMapper;
import logger.Logger;
import reflection.AnnotationFinder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Route implements AnnotationFinder<Method> {
    private String path;
    private final ArrayList<Endpoint> endPoints;

    public Route(Class<?> routeClass) throws IOException {
        path = "/" + routeClass.getPackageName().replaceAll("\\.", "/");

        // Check if the "/routes" string is present; if so remove it else throw an error.
        if (path.contains("/routes")) {
            // If it's just the route path replace it with just '/', if not
            // don't add a double '/'
            if (path.equals("/routes")) path = path.replaceAll("/routes", "/");
            else path = path.replaceAll("/routes", "");
        }
        else throw new IOException("Malformed path! " + routeClass.getName() + " should be in the root directory '/routes'.");

        endPoints = new ArrayList<>();

        var methods = routeClass.getMethods();
        var valid = find(methods, EndpointMapper.class);
        for (var m : valid) endPoints.add(new Endpoint(m));
    }

    public Route(String path, OptionHandler handler, RequestMethod requestMethod) {
        this.path = path;
        endPoints = new ArrayList<>();

        try {
            endPoints.add(new Endpoint(handler, requestMethod));
        } catch (NoSuchMethodException e) {
            Logger.err(e);
        }
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Endpoint> getEndPoints() {
        return endPoints;
    }

    @Override
    public String toString() {
        return "Route{" +
                "path='" + path + '\'' +
                ", endPoints=" + endPoints +
                '}';
    }
}
