package ninja.soroosh.chatopia.core.runner.responses;

import java.io.InputStream;

public sealed interface Response permits TextResponse, PhotoResponse {
    static TextResponse asText(String message) {
        return new TextResponse(message);
    }

    static PhotoResponse asPhoto(InputStream inputStream) {
        return new PhotoResponse(inputStream);
    }
}
