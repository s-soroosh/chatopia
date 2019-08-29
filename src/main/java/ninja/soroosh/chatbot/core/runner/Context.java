package ninja.soroosh.chatbot.core.runner;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ninja.soroosh.chatbot.core.session.Session;

import java.util.Optional;

@Data
@RequiredArgsConstructor
public class Context {
    private final Optional<String> sessionId;
    private Optional<Session> session;
    private final String channel;
}
