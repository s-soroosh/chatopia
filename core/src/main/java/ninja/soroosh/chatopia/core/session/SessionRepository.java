package ninja.soroosh.chatopia.core.session;

public interface SessionRepository {
    Session findById(String sessionId);
}
