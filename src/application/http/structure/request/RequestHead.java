package application.http.structure.request;

import application.http.structure.Head;

public class RequestHead extends Head {
    private final RequestMethod requestMethod;
    private final String path;

    public RequestHead(RequestMethod requestMethod, String path, String version) {
        super(version);
        this.requestMethod = requestMethod;
        this.path = path;
    }

    public RequestHead(RequestHead reqHead) {
        this(reqHead.requestMethod, reqHead.path, reqHead.version);
    }

    /**
     * Implementation from {@link Head}
     *
     * @implNote The first line of a http request is called the "start line".
     *
     * @return The start line of the http request with leading
     * carriage '\r' and newline '\n' chars.
     */
    @Override
    public String getFirstLine() {
        return requestMethod.toString() + " " + path + " " + version + "\r\n";
    }

    /**
     * Similar to {@link RequestHead#getFirstLine()}
     *
     * @return The start line of the http request without carriage and newline chars.
     */
    public String getFirstLineClean() {
        return requestMethod.toString() + " " + path + " " + version;
    }

    public RequestMethod getMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }
}
