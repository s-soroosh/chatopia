package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InlineKeyboardMarkup implements ReplyMarkup {
    private List<List<InlineKeyboardButton>> inlineKeyboard;
}
