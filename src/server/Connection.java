package server;

import application.http.structure.request.RequestMethod;
import application.routing.Route;
import logger.Logger;
import application.http.structure.request.Request;
import application.http.structure.response.Response;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable {
    private final Socket clientSocket;
    private final ArrayList<Route> routes;

    public Connection(Socket clientSocket, ArrayList<Route> routes) {
        this.clientSocket = clientSocket;
        this.routes = routes;
    }

    @Override
    public void run() {
        try {
            var req = new Request(clientSocket);
            var res = new Response(clientSocket);

            String servePath = req.getHead().getPath();

            Logger.info("[+]Serving route: " + servePath);

            for (var route : routes) {
                String path = route.getPath();
                var endPoints = route.getEndPoints();

                for (var e : endPoints) {
                    var method = e.getMethod();
                    var requestMethod = e.getRequestMethod();

                    if (requestMethod.equals(RequestMethod.GET)) {
                       // TODO: implement this
                    }
                }
            }
            closeConnection();
        } catch (Exception e) {
            Logger.err(e);
        }
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
