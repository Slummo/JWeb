package application.http.structure;

import application.http.streams.SocketIO;

import java.net.Socket;

public class Message<T extends Head> extends SocketIO {
    protected T head;
    protected Body body;

    public Message(Socket socket) {
        super(socket);
    }

    public T getHead() {
        return head;
    }

    public Body getBody() {
        return body;
    }
}
