package server;

import application.http.structure.request.RequestMethod;
import application.routing.Route;
import logger.Logger;
import application.http.structure.request.Request;
import application.http.structure.response.Response;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Connection implements Runnable {
    private final Socket clientSocket;
    private final HashMap<String, Route> routesMap;

    public Connection(Socket clientSocket, HashMap<String, Route> routesMap) {
        this.clientSocket = clientSocket;
        this.routesMap = routesMap;
    }

    @Override
    public void run() {
        try {
            var req = new Request(clientSocket);
            var res = new Response(clientSocket);

            String servePath = req.getHead().getPath();
            Logger.info("[+]Serving route: " + servePath);

            var route = routesMap.get(servePath);
            var endPoints = route.getEndPoints();

            for (var e : endPoints) {
                var method = e.getMethod();
                var returnType = method.getReturnType();

                var requestMethod = e.getRequestMethod();

                // Get the route's class new instance using the default constructor
                var instance = method.getDeclaringClass().getConstructor().newInstance();

                switch (requestMethod) {
                    case GET -> {
                        if (String.class.equals(returnType)) {
                            // TODO: fix Temporary implementation
                            if (method.getParameterCount() != 0) continue;

                            String str = (String) method.invoke(instance);
                            res.sendText(str);
                        }
                        if (File.class.equals(returnType)) {
                            File file = (File) method.invoke(instance);
                            res.sendFile(file);
                        }

                        // Only get parameter if String
                        var params = method.getParameters();
                        for (var p : params) {
                            if (p.getType().equals(String.class)) {
                                // TODO: The parameter is a Query parameter
                            }
                        }
                    }
                }
            }
            closeConnection();
        } catch (Exception e) {
            Logger.err(e);
        }
    }

    private void serveRoute(Route r) {

    }

    private boolean shouldKeepAlive(Request req) {
        return req.getHead().getHeaderValue("Connection").equalsIgnoreCase("keep-alive");
    }

    private void closeConnection() {
        try {
            if (!clientSocket.isClosed()) {
                Logger.info("[-]Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
                clientSocket.close();
            }
        } catch (IOException e) {
            Logger.err(e);
        }
    }
}
