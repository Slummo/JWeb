package application.routing;

import application.http.structure.request.Request;
import application.http.structure.response.Response;

@FunctionalInterface
public interface OptionHandler {
    void handle(Request request, Response response);
}
