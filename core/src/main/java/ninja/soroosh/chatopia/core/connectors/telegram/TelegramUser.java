package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.Data;

@Data
public class TelegramUser {
    private int id;
    private boolean isBot;
    private String firstName;
}
