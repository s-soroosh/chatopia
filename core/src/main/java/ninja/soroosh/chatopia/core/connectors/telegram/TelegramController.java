package ninja.soroosh.chatopia.core.connectors.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandRunner;
import ninja.soroosh.chatopia.core.runner.Context;
import ninja.soroosh.chatopia.core.runner.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@ConditionalOnProperty(value = "chatopia.connector.telegram.enabled", havingValue = "true", matchIfMissing = true)
class TelegramController {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private CommandRunner commandRunner;
    @Autowired
    private TelegramCommandBuilder telegramCommandBuilder;

    @Value("${chatopia.connector.telegram.key}")
    private String key;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder.build();
    }


    @PostMapping(path = "/connectors/telegram")
    public String webhook(@RequestBody TelegramRequest telegramRequest) {
        var message = telegramRequest.getMessage() != null ? telegramRequest.getMessage() : telegramRequest.getCallbackQuery().getMessage();
        final long chatId = message.getChat().getId();
        final Command command = telegramCommandBuilder.build(message);
        final String sessionId = "telegram-" + chatId;
        final Response commandResponse = commandRunner.run(
                command,
                new Context(Optional.of(sessionId), "telegram")
        );

        var markup = commandResponse.options().isEmpty() ? null :
                new InlineKeyboardMarkup(List.of(List.of(new InlineKeyboardButton(commandResponse.options().get(0).getText(), commandResponse.options().get(0).getText()))));

        Object response = restTemplate.postForEntity(
                String.format("https://api.telegram.org/bot%s/sendMessage", key),
                new TelegramSendMessage(chatId, commandResponse.message(), markup), Object.class);

        System.out.println(response);
        return "ok";
    }
}

