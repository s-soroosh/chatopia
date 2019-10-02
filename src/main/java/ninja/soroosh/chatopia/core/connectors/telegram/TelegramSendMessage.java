package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class TelegramSendMessage {
    private long chatId;
    private String text;
}
