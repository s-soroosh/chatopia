package ninja.soroosh.chatbot.core.session;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySessionRepository implements SessionRepository {
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Override
    public Session findById(String sessionId) {
        return sessions.computeIfAbsent(sessionId, (id) -> new Session());
    }
}
