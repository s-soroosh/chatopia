package ninja.soroosh.chatopia.core.runner.responses;

import java.io.InputStream;

public sealed interface StreamResponse extends Response permits VideoResponse, PhotoResponse {
    InputStream getStream();
}
