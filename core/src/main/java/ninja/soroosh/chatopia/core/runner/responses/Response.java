package ninja.soroosh.chatopia.core.runner.responses;

import java.io.InputStream;
import java.net.URL;

public sealed interface Response permits TextResponse, PhotoResponse {
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
}
