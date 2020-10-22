package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;

import java.io.InputStream;
import java.net.URL;

@Getter
public final class PhotoResponse implements Response {
    private String caption;
    private InputStream photoStream;
    private URL url;
    private String id;

    PhotoResponse(InputStream photoStream) {
        this.photoStream = photoStream;
    }

    PhotoResponse(URL url) {
        this.url = url;
    }

    PhotoResponse(String id) {
        this.id = id;
    }

    public PhotoResponse withCaption(String caption) {
        this.caption = caption;
        return this;
    }
}
