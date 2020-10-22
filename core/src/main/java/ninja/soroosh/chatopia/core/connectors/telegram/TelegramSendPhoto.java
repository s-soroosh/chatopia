package ninja.soroosh.chatopia.core.connectors.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class TelegramSendPhoto {
    private long chatId;
    private String photo;
    private String caption;
}
