package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;

import java.io.InputStream;

@Getter
public final class PhotoResponse implements Response {
    private final String caption;
    private final InputStream photoStream;

    public PhotoResponse(String caption, InputStream photoStream) {
        this.caption = caption;
        this.photoStream = photoStream;
    }
}
