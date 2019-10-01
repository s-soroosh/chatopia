package ninja.soroosh.chatopia.core.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TelegramSendMessage {
    private long chatId;
    private String text;
}
