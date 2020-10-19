package ninja.soroosh.chatopia.core.runner;

public record CallbackDataOption(String text, String data) implements Option {
    @Override
    public String getText() {
        return this.text;
    }
}

