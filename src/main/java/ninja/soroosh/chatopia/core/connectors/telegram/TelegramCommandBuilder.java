package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandBuilder;
import org.springframework.stereotype.Component;

class TelegramCommandBuilder implements CommandBuilder<TelegramRequest> {
    @Override
    public Command build(TelegramRequest request) {
        return new Command() {

            @Override
            public String name() {
                return request.getMessage().getText();
            }
        };
    }
}
