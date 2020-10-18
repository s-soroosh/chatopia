package ninja.soroosh.chatopia.core.runner;

import lombok.extern.slf4j.Slf4j;
import ninja.soroosh.chatopia.core.Rule;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimpleCommandRunner implements CommandRunner {
    private final List<Rule> rules;
    private final SimpleContextEnricher contextEnricher;
    private static Response defaultResponse = Response.withMessage("Unknown command! \nPlease use command \"help\" to find out what capabilities I have!");
    private final Response helpResponse;

    public SimpleCommandRunner(List<Rule> rules, SimpleContextEnricher contextEnricher) {
        this.rules = rules;
        this.contextEnricher = contextEnricher;
        this.helpResponse = Response.withMessage(
                rules.stream()
                        .map(rule -> rule.getCommandName() + " - " + rule.getHelp())
                        .collect(Collectors.joining("\n"))
        );
    }

    @Override
    public Response run(final Command command, final Context context) {
        final Optional<Rule> maybeMatchedRule = this.rules.stream()
                .filter(rule -> rule.getCommandName().equals(command.name()))
                .findFirst();
        if (maybeMatchedRule.isEmpty()) {
            if (command.name().equals("help")) {
                return helpResponse;
            }
            return defaultResponse;
        }

        final Context enrichedContext = contextEnricher.enrich(context);
        final Rule matchedRule = maybeMatchedRule.get();

        return runCommand(command, enrichedContext, matchedRule);
    }

    private Response runCommand(Command command, Context enrichedContext, Rule matchedRule) {
        try {
            return (Response) matchedRule.getMethod().invoke(
                    matchedRule.getObject(),
                    command.name(),
                    enrichedContext
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error In running command", e);
            return defaultResponse;
        }
    }
}
