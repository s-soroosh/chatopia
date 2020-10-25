package ninja.soroosh.chatopia.core.runner;

import ninja.soroosh.chatopia.core.runner.responses.Response;

public interface CommandRunner {
    Response run(Command command, Context context);

    Response runEvent(Event<?> event, Context context);
}
