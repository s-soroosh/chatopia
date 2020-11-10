package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.User;

class TelegramCommandBuilder {

    public Command buildFrom(TelegramMessage message) {
        return new Command() {
            @Override
            public String name() {
                return message.getText();
            }

            @Override
            public User commander() {
                return new User(String.valueOf(message.getFrom().getId()), message.getFrom().getFirstName(), "");
            }
        };
    }

    public Command buildFrom(CallbackQuery callbackQuery) {
        return new Command() {
            @Override
            public String name() {
                return callbackQuery.getData();
            }

            @Override
            public User commander() {
                return new User(String.valueOf(callbackQuery.getFrom().getId()), callbackQuery.getFrom().getFirstName(), "");
            }
        };
    }
}
