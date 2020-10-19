package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandBuilder;

class TelegramCommandBuilder implements CommandBuilder<String> {
    @Override
    public Command build(String message) {
        return () -> message;
    }
}
