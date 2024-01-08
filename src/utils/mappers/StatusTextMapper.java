package utils.mappers;

import java.util.HashMap;

public class StatusTextMapper {
    private static final HashMap<Integer, String> map = new HashMap<>();

    static {
        map.put(100, "Continue");
        map.put(101, "Switching Protocols");
        map.put(200, "OK");
        map.put(201, "Created");
        map.put(202, "Accepted");
        map.put(204, "No Content");
        map.put(300, "Multiple Choices");
        map.put(301, "Moved Permanently");
        map.put(302, "Found");
        map.put(304, "Not Modified");
        map.put(400, "Bad Request");
        map.put(401, "Unauthorized");
        map.put(403, "Forbidden");
        map.put(404, "Not Found");
        map.put(405, "Method Not Allowed");
        map.put(500, "Internal Server Error");
        map.put(501, "Not Implemented");
        map.put(503, "Service Unavailable");
    }

    public static String getStatusText(int status) {
        return map.getOrDefault(status, "Internal Server Error");
    }
}
