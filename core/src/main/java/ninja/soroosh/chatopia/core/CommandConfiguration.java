package ninja.soroosh.chatopia.core;

import ninja.soroosh.chatopia.core.annotation.ChatController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class CommandConfiguration {

    @Bean
    public RulesBuilder rulesBuilder() {
        return new RulesBuilder();
    }

    @Bean
    public EventsBuilder eventsBuilder() {
        return new EventsBuilder();
    }

    @Bean
    public List<CommandRule> commandRules(ApplicationContext applicationContext, RulesBuilder rulesBuilder) {
        final Map<String, Object> chatControllers = applicationContext.getBeansWithAnnotation(ChatController.class);
        return rulesBuilder.buildFromOnCommandAnnotations(chatControllers.values());
    }

    @Bean
    public List<EventRule> eventRules(ApplicationContext applicationContext, EventsBuilder eventsBuilder) {
        final Map<String, Object> chatControllers = applicationContext.getBeansWithAnnotation(ChatController.class);
        return eventsBuilder.buildFromOnCommandAnnotations(chatControllers.values());
    }
}
