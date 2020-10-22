package ninja.soroosh.chatopia.core.runner.responses;

public sealed interface Response permits TextResponse, PhotoResponse {
    static TextResponse asText(String message) {
        return new TextResponse(message);
    }
}
