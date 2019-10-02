package ninja.soroosh.chatopia.core.runner;

public interface CommandRunner {
    Response run(Command command, Context context);
}
