package ninja.soroosh.chatopia.core.runner;

public interface Command {
    String name();
    User commander();
}
