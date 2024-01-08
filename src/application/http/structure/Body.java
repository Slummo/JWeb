package application.http.structure;

import java.io.File;

public class Body {
    private Object content;

    public Body(String content) {
        this.content = content;
    }

    public Body(File content) {
        this.content = content;
    }

    public Body() {}

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
