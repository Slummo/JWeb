package application.http.structure.response;

import logger.Logger;
import application.http.structure.Body;
import application.http.structure.Message;
import utils.files.FS;
import utils.mappers.ContentTypeMapper;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;

public class Response extends Message<ResponseHead> {
    public Response(Socket socket) {
        super(socket);
        head = new ResponseHead();
        body = new Body();
    }

    private void sendResponse() {
        writeString(head.toString());
        Logger.info("[+]Sent " + head.getHeaders().size() + " response headers");

        var content = body.getContent();
        if(content != null) {
            if (content instanceof String s) writeString(s);
            else if (content instanceof File f) writeFile(f);

            Logger.info("[+]Sent body");
        }
    }

    public Response status(int statusCode) {
        head.setStatus(statusCode);
        return this;
    }

    public void sendFile(File file) {
        try {
            var extension = FS.getFileExtension(file.getName());

            if (!file.exists()) {
                status(404);
                sendResponse();
                return;
            }
            if (file.isDirectory()) {
                status(500);
                sendResponse();
                return;
            }

            status(200);

            // Set head
            head.setHeader("Connection", "keep-alive");
            head.setHeader("Content-Type", ContentTypeMapper.getContentType(extension));
            head.setHeader("Content-Length", String.valueOf(file.length()));

            // Set body
            body.setContent(file);

            sendResponse();
        } catch (Exception e) {
            status(500);
            Logger.err(e);
        }
    }

    public void sendFile(String path) {
        sendFile(new File(path));
    }

    public void sendFile(Path path) {
        sendFile(path.toString());
    }

    public void sendText(String text) {
        status(200);

        // Set head
        head.setHeader("Connection", "keep-alive");
        head.setHeader("Content-Type", ContentTypeMapper.getContentType("txt"));
        head.setHeader("Content-Length", String.valueOf(text.length()));

        // Set body
        body.setContent(text);

        sendResponse();
    }
}
