package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.ChatEventPayload;
import ninja.soroosh.chatopia.core.runner.Event;
import ninja.soroosh.chatopia.core.runner.UserEventPayload;

public class TelegramEventBuilder {
    public Event build(TelegramMessage message) {
        if (message.getNewChatMember() != null) {
            return new Event("NEW_CHAT_MEMBER", new UserEventPayload(
                    String.valueOf(message.getNewChatMember().getId()),
                    message.getNewChatMember().getFirstName(),
                    ""
            ));
        } else if (message.getLeftChatMember() != null) {

            return new Event(
                    "NEW_CHAT_MEMBER",
                    new UserEventPayload(
                            String.valueOf(message.getNewChatMember().getId()),
                            message.getNewChatMember().getFirstName(),
                            ""
                    ));
        } else if (message.getNewChatTitle() != null) {
            return new Event(
                    "NEW_CHAT_TITLE",
                    new ChatEventPayload(message.getNewChatTitle())
            );
        } else {
            return new Event("UNKNOWN", null);
        }
    }
}
