package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.*;
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
import java.util.stream.Collectors;

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
        var commandText = telegramRequest.getCallbackQuery() == null ? message.getText() : telegramRequest.getCallbackQuery().getData();
        final long chatId = message.getChat().getId();
        final Command command = telegramCommandBuilder.build(commandText);
        final String sessionId = "telegram-" + chatId;
        final Response commandResponse = commandRunner.run(
                command,
                new Context(Optional.of(sessionId), "telegram")
        );

        var optionsMarkup = generateOptionsMark(commandResponse.getOptions());

        Object response = restTemplate.postForEntity(
                String.format("https://api.telegram.org/bot%s/sendMessage", key),
                new TelegramSendMessage(chatId, commandResponse.getMessage(), optionsMarkup), Object.class);

        System.out.println(response);
        return "ok";
    }

    private ReplyMarkup generateOptionsMark(List<Option> options) {
        if (options == null || options.isEmpty()) {
            return null;
        }
        final var markups = options
                .stream()
                .map(option -> {
                            if (option instanceof CallbackURLOption callbackURLOption) {
                                return new InlineKeyboardButton(callbackURLOption.getText(), null, callbackURLOption.url());
                            } else if (option instanceof CallbackDataOption callbackDataOption) {
                                return new InlineKeyboardButton(callbackDataOption.getText(), callbackDataOption.data(), null);
                            } else {
                                return new InlineKeyboardButton(option.getText(), "EMPTY_DATA", null);
                            }
                        }
                ).collect(Collectors.toList());
        return new InlineKeyboardMarkup(List.of(markups));
    }
}

