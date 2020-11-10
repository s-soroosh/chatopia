package ninja.soroosh.chatopia.core.runner;

import lombok.extern.slf4j.Slf4j;
import ninja.soroosh.chatopia.core.CommandRule;
import ninja.soroosh.chatopia.core.EventRule;
import ninja.soroosh.chatopia.core.runner.responses.Response;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SimpleCommandRunner implements CommandRunner {
    private final List<CommandRule> commandRules;
    private final List<EventRule> eventRules;
    private final SimpleContextEnricher contextEnricher;
    private static Response defaultResponse = Response.asText("Unknown command! \nPlease use command \"help\" to find out what capabilities I have!");
    private final Response helpResponse;

    public SimpleCommandRunner(List<CommandRule> commandRules, List<EventRule> eventRules, SimpleContextEnricher contextEnricher) {
        this.commandRules = commandRules;
        this.eventRules = eventRules;
        this.contextEnricher = contextEnricher;
        this.helpResponse = Response.asText(
                commandRules.stream()
                        .map(commandRule -> commandRule.getCommandName() + " - " + commandRule.getHelp())
                        .collect(Collectors.joining("\n"))
        );
    }

    @Override
    public Response run(final Command command, final Context context) {
        final Optional<CommandRule> maybeMatchedRule = this.commandRules.stream()
                .filter(commandRule -> command.name().equals(commandRule.getCommandName())
                        || (commandRule.getCommandName().endsWith(" *")
                        && command.name().startsWith(commandRule.getCommandName().substring(0, commandRule.getCommandName().lastIndexOf("\s")))))
                .findFirst();
        if (maybeMatchedRule.isEmpty()) {
            if (command.name().equals("help")) {
                return helpResponse;
            }
            return defaultResponse;
        }

        final Context enrichedContext = contextEnricher.enrich(context);
        final CommandRule matchedCommandRule = maybeMatchedRule.get();

        return runCommand(command, enrichedContext, matchedCommandRule);
    }

    @Override
    public Response runEvent(final Event<?> event, final Context context) {
        final Optional<EventRule> maybeMatchedRule = this.eventRules.stream()
                .filter(eventRule -> eventRule.getEventName().equals(event.getEventName()))
                .findFirst();
        if (maybeMatchedRule.isEmpty()) {
            log.warn("unhandled event {}", event);
            // TODO: EMPTY Response
            return defaultResponse;
        }

        final Context enrichedContext = contextEnricher.enrich(context);
        final EventRule matchedEventRule = maybeMatchedRule.get();

        return runEvent(event, enrichedContext, matchedEventRule);
    }

    private Response runCommand(Command command, Context enrichedContext, CommandRule matchedCommandRule) {
        try {
            return (Response) matchedCommandRule.getMethod().invoke(
                    matchedCommandRule.getObject(),
                    command,
                    enrichedContext
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error In running command", e);
            return defaultResponse;
        }
    }

    public Response runEvent(Event<?> event, Context enrichedContext, EventRule eventRule) {
        try {
            return (Response) eventRule.getMethod().invoke(eventRule.getObject(), event, enrichedContext);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error In running command", e);
            return defaultResponse;
        }
    }
}
