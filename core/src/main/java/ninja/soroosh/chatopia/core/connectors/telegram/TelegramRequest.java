package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.Data;

@Data
class TelegramRequest {
    private String updateId;
    private TelegramMessage message;
}
