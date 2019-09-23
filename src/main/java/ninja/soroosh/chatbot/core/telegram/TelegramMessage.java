package ninja.soroosh.chatbot.core.telegram;


import lombok.Data;

@Data
public class TelegramMessage {
    private String messageId;
    private Chat chat;
    private String text;
    private long date;
}
