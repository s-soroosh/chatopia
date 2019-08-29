package ninja.soroosh.chatbot.core.runner;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class Context {
    private Optional<String> sessionId;
    private String channel;
}
