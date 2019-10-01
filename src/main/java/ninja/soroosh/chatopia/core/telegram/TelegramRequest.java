package ninja.soroosh.chatopia.core.telegram;

import lombok.Data;

@Data
public class TelegramRequest {
    private String updateId;
    private TelegramMessage message;
}
