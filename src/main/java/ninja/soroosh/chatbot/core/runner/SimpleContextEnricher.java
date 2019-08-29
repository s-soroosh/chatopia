package ninja.soroosh.chatbot.core.runner;

import ninja.soroosh.chatbot.core.session.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleContextEnricher {
    @Autowired
    private SessionRepository sessionRepository;

    public Context enrich(Context context) {
        if (context.getSessionId().isPresent()) {
            context.setSession(
                    Optional.of(sessionRepository.findById(context.getSessionId().get()))
            );
        }
        return context;
    }
}
