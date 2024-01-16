package server;

import application.routing.Route;
import logger.LogConfig;
import logger.Logger;
import logger.NewLinePosition;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class WebServer {
    private ServerSocket serverSocket;
    private boolean isOn;

    public WebServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            isOn = true;
        } catch (Exception e) {
            Logger.err(e);
        }
    }

    @LogConfig(position = NewLinePosition.BEFORE)
    public void serveRoutes(ArrayList<Route> routes) {
        try {
            while (isOn) {
                var clientSocket = serverSocket.accept();
                Logger.info("[+]Client connected: " + clientSocket.getInetAddress().getHostAddress());

                clientSocket.setKeepAlive(true);
                clientSocket.setSoTimeout(5000);

                new Thread(new Connection(clientSocket, routes)).start();
            }
        } catch (IOException e) {
            Logger.err(e);
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                    Logger.info("[+]Server stopped");
                } catch (IOException e) {
                    Logger.err(e);
                }
            }
        }
    }

    public void stop() {
        isOn = false;
    }
}
