package ninja.soroosh.chatbot.core.runner;

import ninja.soroosh.chatbot.core.Rule;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleCommandRunner implements CommandRunner {
    private final List<Rule> rules;
    private final SimpleContextEnricher contextEnricher;
    private static Response defaultResponse = () -> "Unknown command!";

    public SimpleCommandRunner(List<Rule> rules, SimpleContextEnricher contextEnricher) {
        this.rules = rules;
        this.contextEnricher = contextEnricher;
    }

    @Override
    public Response run(final Command command, final Context context) {
        final Optional<Rule> maybeMatchedRule = this.rules.stream()
                .filter(rule -> rule.getCommandName().equals(command.name()))
                .findFirst();
        if (maybeMatchedRule.isEmpty()) {
            return defaultResponse;
        }

        final Context enrichedContext = contextEnricher.enrich(context);

        // TODO: improve the implementation
        final Rule matchedRule = maybeMatchedRule.get();
        try {
            return (Response) matchedRule.getMethod().invoke(matchedRule.getObject(), command.name(), enrichedContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return defaultResponse;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return defaultResponse;
        }
    }
}
