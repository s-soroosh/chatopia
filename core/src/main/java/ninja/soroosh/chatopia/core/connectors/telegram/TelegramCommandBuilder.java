package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandBuilder;
import org.springframework.stereotype.Component;

class TelegramCommandBuilder implements CommandBuilder<TelegramMessage> {
    @Override
    public Command build(TelegramMessage message) {
        return () -> message.getText();
    }
}
