package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@ConditionalOnProperty(value = "chatopia.connector.telegram.enabled", havingValue = "true", matchIfMissing = true)
public class TelegramConfiguration {
    public TelegramConfiguration() {
        log.info("Setting up telegram connector...");
        log.info("The hook endpoint is: /connectors/telegram");
    }

    @Bean
    public TelegramCommandBuilder getTelegramCommandBuilder() {
        return new TelegramCommandBuilder();
    }
}
