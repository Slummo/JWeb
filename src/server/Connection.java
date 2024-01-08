package server;

import application.options.OptionHandler;
import logger.Logger;
import application.http.structure.request.Request;
import application.http.structure.response.Response;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Connection implements Runnable {
    private final Socket clientSocket;
    private final HashMap<String, OptionHandler> routes;

    public Connection(Socket clientSocket, HashMap<String, OptionHandler> routes) {
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

            var handler = routes.get(servePath);
            if (handler != null) {
                handler.handle(req, res);
            } else {
                // TODO: Respond with a 404 Not Found error
                res.status(404).sendText("Not Found");
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
