package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;

import java.io.InputStream;

@Getter
public final class PhotoResponse implements Response {
    private String caption;
    private final InputStream photoStream;

    public PhotoResponse(InputStream photoStream) {
        this.photoStream = photoStream;
    }

    public PhotoResponse withCaption(String caption) {
        this.caption = caption;
        return this;
    }
}
