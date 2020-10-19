package ninja.soroosh.chatopia.core.runner;

public record CallbackURLOption(String text, String url) implements Option {
    @Override
    public String getText() {
        return this.text;
    }
}
