package application.http.structure.request;

public enum RequestMethod {
    GET, POST, PATCH, PUT, DELETE;

    public static RequestMethod from(String str) {
        str = str.toUpperCase();
        if(str.equals("GET")) return RequestMethod.GET;
        if(str.equals("POST")) return RequestMethod.POST;
        if(str.equals("PATCH")) return RequestMethod.PATCH;
        if(str.equals("PUT")) return RequestMethod.PUT;
        if(str.equals("DELETE")) return RequestMethod.DELETE;

        return null;
    }
}
