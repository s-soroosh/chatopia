package ninja.soroosh.chatbot.core.runner;

import ninja.soroosh.chatbot.core.Rule;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleCommandRunner implements CommandRunner {
    private final List<Rule> rules;
    private static Response defaultResponse = () -> "Unknown command!";

    public SimpleCommandRunner(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public Response run(Command command) {
        final Optional<Rule> matchedRule = this.rules.stream()
                .filter(rule -> rule.getCommandName().equals(command.name()))
                .findFirst();

        // TODO: improve the implementation
        return matchedRule.map(rule -> {
            try {
                return (Response) rule.getMethod().invoke(rule.getObject(), command.name());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return defaultResponse;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return defaultResponse;
            }
        }).orElse(defaultResponse);
    }
}
