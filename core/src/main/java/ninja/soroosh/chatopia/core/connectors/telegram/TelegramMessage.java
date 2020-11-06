package ninja.soroosh.chatopia.core.connectors.telegram;


import lombok.Data;

@Data
class TelegramMessage {
    private String messageId;
    private Chat chat;
    private String text;
    private long date;
    private TelegramUser newChatMember;
    private TelegramUser leftChatMember;
    private String newChatTitle;
}
