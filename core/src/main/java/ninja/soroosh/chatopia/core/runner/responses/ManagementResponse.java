package ninja.soroosh.chatopia.core.runner.responses;

import ninja.soroosh.chatopia.core.runner.User;

public class ManagementResponse {
    ManagementResponse() {
    }

    static Response kick(User user, String conversationId) {
        return new KickResponse(user, conversationId);
    }

}
