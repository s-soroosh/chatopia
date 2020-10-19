package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InlineKeyboardButton {
    private String text;
    private String callbackData;
//    private String
}
