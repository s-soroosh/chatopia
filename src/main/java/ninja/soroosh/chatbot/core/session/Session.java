package ninja.soroosh.chatbot.core.session;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
    private Map<String, String> sessionMap = new ConcurrentHashMap<>();

    public Optional<String> get(String key) {
        return Optional.ofNullable(sessionMap.getOrDefault(key, null));
    }

    public void set(String key, String value) {
        this.sessionMap.put(key, value);
    }
}
