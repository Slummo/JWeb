package application.http.structure.response;

import application.http.structure.Head;
import utils.mappers.StatusTextMapper;

public class ResponseHead extends Head {
    private ResponseStatus responseStatus;

    public ResponseHead(String version, ResponseStatus responseStatus) {
        super(version);
        this.responseStatus = responseStatus;
    }

    public ResponseHead(String version, int statusCode) {
        this(version, new ResponseStatus(statusCode, StatusTextMapper.getStatusText(statusCode)));
    }

    public ResponseHead() {
        this("HTTP/1.1", 200);
    }

    /**
     * Implementation from {@link Head}
     *
     * @implNote The first line of a http request is called the "status line".
     *
     * @return The status line of the http request.
     */
    @Override
    public String getFirstLine() {
        return version + " " + responseStatus.toString() + "\r\n";
    }

    public ResponseStatus getStatus() {
        return responseStatus;
    }

    public void setStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setStatus(int statusCode) {
        this.responseStatus = new ResponseStatus(statusCode, StatusTextMapper.getStatusText(statusCode));
    }
}
