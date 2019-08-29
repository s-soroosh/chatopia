package ninja.soroosh.chatbot.core.session;

public interface SessionRepository {
    Session findById(String sessionId);
}
