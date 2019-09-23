package ninja.soroosh.chatbot.core.telegram;

import lombok.Data;

@Data
public class TelegramRequest {
    private String updateId;
    private TelegramMessage message;
}
