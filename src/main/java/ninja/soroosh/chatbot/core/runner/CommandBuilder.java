package ninja.soroosh.chatbot.core.runner;

public interface CommandBuilder<T> {
    Command build(T input);
}
