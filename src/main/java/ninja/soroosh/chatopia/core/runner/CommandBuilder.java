package ninja.soroosh.chatopia.core.runner;

public interface CommandBuilder<T> {
    Command build(T input);
}
