package utils.mappers;

import java.util.HashMap;
import java.util.Map;

public class ContentTypeMapper {
    private static final Map<String, String> map = new HashMap<>();

    static {
        // Text files
        map.put("txt", "text/plain");
        map.put("html", "text/html");
        map.put("css", "text/css");
        map.put("js", "application/javascript");
        map.put("xml", "application/xml");

        // Image files
        map.put("png", "image/png");
        map.put("jpg", "image/jpeg");
        map.put("jpeg", "image/jpeg");
        map.put("gif", "image/gif");
        map.put("bmp", "image/bmp");
        map.put("ico", "image/avif");

        // Audio files
        map.put("mp3", "audio/mpeg");
        map.put("wav", "audio/wav");
        map.put("ogg", "audio/ogg");

        // Video files
        map.put("mp4", "video/mp4");
        map.put("avi", "video/x-msvideo");
        map.put("mkv", "video/x-matroska");

        // Application files
        map.put("exe", "application/octet-stream");
        map.put("pdf", "application/pdf");
        map.put("zip", "application/zip");
        map.put("json", "application/json");

        // Compressed files
        map.put("gz", "application/gzip");
        map.put("tar", "application/x-tar");
        map.put("7z", "application/x-7z-compressed");
    }

    public static String getContentType(String extension) {
        return map.getOrDefault(extension.toLowerCase(), "application/octet-stream");
    }
}
