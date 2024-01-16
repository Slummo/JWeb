package application;

import application.http.structure.request.RequestMethod;
import application.options.folder.FolderOption;
import application.routing.Route;
import application.routing.config.RouteMapper;
import logger.Logger;
import application.options.OptionConfig;
import application.options.folder.FolderConfig;
import application.options.icon.IconOption;
import application.options.icon.IconConfig;
import application.options.Option;
import application.options.log.LogOption;
import application.options.log.LogConfig;
import reflection.AnnotationFinder;
import server.callbacks.CreationCallback;
import server.WebServer;
import application.options.OptionHandler;
import utils.RF;

import java.io.IOException;
import java.util.ArrayList;

public class Application implements AnnotationFinder<Class<?>> {
    private final ArrayList<Route> routes;

    public Application() {
        routes = new ArrayList<>();

        var classes = RF.getClassesInDir(System.getProperty("user.dir"));
        var routeMappers = find(classes, RouteMapper.class);

        for (var mapper : routeMappers) {
            try {
                routes.add(new Route(mapper));
            } catch (IOException e) {
                Logger.err(e);
            }
        }
    }

    /**
     * This method is used to configure certain aspects of the web server behaviour.
     *
     * <p>Options:</p>
     * <p>
     *      {@link FolderOption} is used to serve static files (like html documents, images, videos, script etc...)
     *      inside a folder.
     * </p>
     * <p>{@link LogOption} is used to add the customizable logging system to the application.</p>
     * <p>{@link IconOption} is used to serve the icon of a web page inside a folder.</p>
     *
     * <p>
     *     Regarding {@link IconOption} and {@link LogOption} specifically, only the first option of these types will be used,
     *     and every other call will be <strong>ignored</strong>.
     * </p>
     *
     * @param config The configuration instance used to configure the web server behaviour.
     *
     * @see Option
     */

    public void use(OptionConfig config) {
        if (config instanceof FolderConfig c) {
            var folder = new FolderOption(c);
            folder.getFilesPaths().forEach(path -> {
                var relativePath = folder.getRelativePath(path);
                request(relativePath, (req, res) -> res.sendFile(path), RequestMethod.GET);
            });
        }
        if (config instanceof IconConfig c) {
            var icon = new IconOption(c);
            if (!icon.isSet()) icon.set();
            else return;
            request("/favicon.ico", (req, res) -> res.sendFile(icon.getPath()), RequestMethod.GET);
        }
        if (config instanceof LogConfig c) {
           var log = new LogOption(c);
            if (!log.isSet()) log.set();
            else return;
           // TODO make something with log
        }
    }

    private void request(String path, OptionHandler optionHandler, RequestMethod requestMethod) {
        routes.add(new Route(path, optionHandler, requestMethod));
    }

    public void start(int port, CreationCallback callback) {
        try {
            var server = new WebServer(port);
            callback.exec();

            server.serveRoutes(routes);
        } catch (Exception e) {
            Logger.err(e);
        }
    }
}
