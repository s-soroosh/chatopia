package ninja.soroosh.chatopia.core.runner.responses;

import java.io.InputStream;
import java.net.URL;

public sealed interface Response permits TextResponse, PhotoResponse, VideoResponse {
    static TextResponse asText(String message) {
        return new TextResponse(message);
    }

    static PhotoResponse asPhoto(InputStream inputStream) {
        return new PhotoResponse(inputStream);
    }

    static PhotoResponse asPhoto(URL url) {
        return new PhotoResponse(url);
    }

    static PhotoResponse asPhoto(String id) {
        return new PhotoResponse(id);
    }

    static VideoResponse asVideo(InputStream inputStream) {
        return new VideoResponse(inputStream);
    }

    static VideoResponse asVideo(URL url) {
        return new VideoResponse(url);
    }

    static VideoResponse asVideo(String id) {
        return new VideoResponse(id);
    }

    InputStream getStream();

    String getCaption();
}
