package application.http.structure;

import java.util.HashMap;

public abstract class Head {
    protected final String version;
    protected final HashMap<String, String> headers;

    public Head(String version) {
        this.version = version;
        headers = new HashMap<>();
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * Abstract method to get the first line of a http message, which varies if it belongs
     * to a request or a response.
     *
     * @return  The first line of a http request/response with proper newline chars ('\r\n').
     */
    public abstract String getFirstLine();

    public String getVersion() {
        return version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Delegate method of {@link HashMap#containsKey(Object)}.
     * @see HashMap
     */
    public boolean isHeaderSet(String headerName) {
        return headers.containsKey(headerName);
    }

    /**
     * Delegate method of {@link HashMap#get(Object)}.
     * @see HashMap
     */
    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }

    public String getHeaderAsString(String headerName) {
        return headerName + ": " + getHeaderValue(headerName);
    }

    public String getHeadersAsString() {
        var str = new StringBuilder();
        headers.forEach((name, value) -> str.append(name).append(": ").append(value).append("\r\n"));
        return str.toString();
    }

    @Override
    public String toString() {
        return getFirstLine() + getHeadersAsString();
    }
}
