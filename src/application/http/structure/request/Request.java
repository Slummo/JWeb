package application.http.structure.request;

import logger.Logger;
import application.http.structure.Body;
import application.http.structure.Message;

import java.net.Socket;
import java.util.regex.Pattern;


public class Request extends Message<RequestHead> {
    public Request(Socket socket) {
        super(socket);

        initHead();

        body = new Body();
    }

    private void initHead() {
        String requestString = readString();

        // Extracting request line
        var r1 = Pattern.compile("([A-Z]+)\\s+(\\S+)\\s+(HTTP/\\d\\.\\d)");
        var m1 = r1.matcher(requestString);
        if (m1.find()) {
            head = new RequestHead(RequestMethod.from(m1.group(1)), m1.group(2), m1.group(3));

            Logger.info("[+]Received request line: " + head.getFirstLineClean());
        }

        // Extracting headers
        var r2 = Pattern.compile("(\\S+):\\s*(.+)");
        var m2 = r2.matcher(requestString);
        int count = 0;
        while (m2.find()) {
            head.setHeader(m2.group(1), m2.group(2));
            count++;
        }

        Logger.info("[+]Received " + count + " request headers");
    }
}
