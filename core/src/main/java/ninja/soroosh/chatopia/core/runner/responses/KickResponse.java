package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;
import ninja.soroosh.chatopia.core.runner.User;

@Getter
public record KickResponse(User user, String conversationId) implements Response {

    @Override
    public String getCaption() {
//        TODO: remove this field
        return null;
    }
}
