package application.http.structure.response;

public record ResponseStatus(int code, String text) {
    @Override
    public String toString() {
        return code + " " + text;
    }
}
