package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;

import java.io.InputStream;
import java.net.URL;

@Getter
public final class VideoResponse implements Response {
    private String caption;
    private InputStream videoStream;
    private URL url;
    private String id;

    VideoResponse(InputStream videoStream) {
        this.videoStream = videoStream;
    }

    VideoResponse(URL url) {
        this.url = url;
    }

    VideoResponse(String id) {
        this.id = id;
    }

    public InputStream getStream() {
        return this.videoStream;
    }

    public VideoResponse withCaption(String caption) {
        this.caption = caption;
        return this;
    }
}
