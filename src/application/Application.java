package application;

import logger.Logger;
import application.options.OptionConfig;
import application.options.folder.FolderConfig;
import application.options.icon.Icon;
import application.options.icon.IconConfig;
import application.options.folder.Folder;
import application.options.Option;
import application.options.log.Log;
import application.options.log.LogConfig;
import server.callbacks.CreationCallback;
import server.WebServer;
import application.options.OptionHandler;

import java.util.HashMap;

public class Application {
    private final HashMap<String, OptionHandler> routes;

    public Application() {
        routes = new HashMap<>();
    }

    /**
     * This method is used to configure certain aspects of the web server behaviour.
     *
     * <p>Options:</p>
     * <p>
     *      {@link Folder} is used to serve static files (like html documents, images, videos, script etc...)
     *      inside a folder.
     * </p>
     * <p>{@link Log} is used to add the customizable logging system to the application.</p>
     * <p>{@link Icon} is used to serve the icon of a web page inside a folder.</p>
     *
     * <p>
     *     Regarding {@link Icon} and {@link Log} specifically, only the first option of these types will be used,
     *     and every other call will be <strong>ignored</strong>.
     * </p>
     *
     * @param config The configuration instance used to configure the web server behaviour.
     *
     * @see Option
     */

    public void use(OptionConfig config) {
        if (config instanceof FolderConfig c) {
            var folder = new Folder(c);
            folder.getFilesPaths().forEach(path -> {
                var relativePath = folder.getRelativePath(path);
                get(relativePath, (req, res) -> res.sendFile(path));
            });
        }
        if (config instanceof IconConfig c) {
            var icon = new Icon(c);
            if (!icon.isSet()) icon.set();
            else return;
            get("/favicon.ico", (req, res) -> res.sendFile(icon.getPath()));
        }
        if (config instanceof LogConfig c) {
           var log = new Log(c);
            if (!log.isSet()) log.set();
            else return;
           // TODO make something with log
        }
    }

    private void get(String path, OptionHandler optionHandler) {
        routes.put(path, optionHandler);
    }

    private void post(String path, OptionHandler optionHandler) {
        // TODO
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
