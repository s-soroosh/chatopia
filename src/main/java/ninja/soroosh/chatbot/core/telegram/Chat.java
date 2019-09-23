package ninja.soroosh.chatbot.core.telegram;

import lombok.Data;

@Data
public class Chat {
    private long id;
    private String firstName;
    private String username;
    private String type;
}
