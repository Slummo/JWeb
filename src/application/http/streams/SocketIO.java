package application.http.streams;

import logger.Logger;

import java.io.*;
import java.net.Socket;

public class SocketIO {
    private final int BUF_SIZE = 4096;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;

    public SocketIO(Socket socket) {
        try {
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            Logger.err(e);
        }
    }

    protected byte[] readBytes() {
        var bArrOutStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUF_SIZE];

        try {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bArrOutStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Logger.err(e);
        }

        Logger.info("[+]Read bytes " + "(" + bArrOutStream.size() + ")");

        return bArrOutStream.toByteArray();
    }

    protected String readString() {
        var content = new StringBuilder();
        byte[] buffer = new byte[BUF_SIZE];

        try {
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                content.append(new String(buffer, 0, bytesRead));
                if (content.toString().endsWith("\r\n\r\n")) break;
            }
        } catch (IOException e) {
            Logger.err(e);
        }

        Logger.info("[+]Read string " + "(" + content.length() + " chars)");

        return content.toString();
    }

    protected void writeString(String str) {
        try {
            outputStream.write(str.getBytes());
            outputStream.flush();

            Logger.info("[+]Wrote string " + "(" + str.length() + " chars)");
        } catch (IOException e) {
            Logger.err(e);
        }
    }

    protected File readFile(String path) {
        byte[] buf = new byte[BUF_SIZE];
        int bytesRead;
        int offset = 0;

        var file = new File(path);

        try (var fOutStream = new FileOutputStream(file)) {
            while((bytesRead = inputStream.read(buf, offset, buf.length)) != -1) {
                fOutStream.write(buf);
                offset += bytesRead;
            }

            fOutStream.flush();
        } catch (IOException e) {
            Logger.err(e);
        }

        Logger.info("[+]Read file " + "(name: " + file.getName() + ")");

        return file;
    }

    protected void writeFile(File file) {
        byte[] buf = new byte[BUF_SIZE];
        int bytesRead;

        try (var fInStream = new FileInputStream(file)) {
            while ((bytesRead = fInStream.read(buf)) != -1) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.flush();

            Logger.info("[+]Wrote file " + "(name: " + file.getName() + ")");
        } catch (IOException e) {
            Logger.err(e);
        }
    }

    protected void writeFile(String path) {
        writeFile(new File(path));
    }
}
