package application.routing;

import application.http.structure.request.Request;
import application.http.structure.request.RequestMethod;
import application.http.structure.response.Response;
import application.options.OptionHandler;
import application.routing.config.EndpointMapper;

import java.lang.reflect.Method;

public class Endpoint {
    private final Method method;
    private final RequestMethod requestMethod;

    public Endpoint(Method endPointMethod) {
        method = endPointMethod;
        requestMethod = endPointMethod.getAnnotation(EndpointMapper.class).requestMethod();
    }

    public Endpoint(Method endPointMethod, RequestMethod requestMethod) {
        method = endPointMethod;
        this.requestMethod = requestMethod;
    }

    public Endpoint(OptionHandler handler, RequestMethod requestMethod) throws NoSuchMethodException {
        this(handler.getClass().getMethod("handle", Request.class, Response.class), requestMethod);
    }

    public Method getMethod() {
        return method;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "method=" + method +
                ", requestMethod=" + requestMethod +
                '}';
    }
}
