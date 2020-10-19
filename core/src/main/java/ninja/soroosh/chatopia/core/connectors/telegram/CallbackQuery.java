package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.Data;

@Data
public class CallbackQuery {
    private TelegramMessage message;
    private String data;
}
