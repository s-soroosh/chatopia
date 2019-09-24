package ninja.soroosh.chatbot.core.telegram;

import ninja.soroosh.chatbot.core.runner.Command;
import ninja.soroosh.chatbot.core.runner.CommandBuilder;
import org.springframework.stereotype.Component;

@Component
public class TelegramCommandBuilder implements CommandBuilder<TelegramRequest> {
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
