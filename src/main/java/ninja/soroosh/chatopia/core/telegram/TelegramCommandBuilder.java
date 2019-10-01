package ninja.soroosh.chatopia.core.telegram;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandBuilder;
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
