package ninja.soroosh.chatopia.core.connectors.telegram;

import ninja.soroosh.chatopia.core.runner.*;
import ninja.soroosh.chatopia.core.runner.responses.PhotoResponse;
import ninja.soroosh.chatopia.core.runner.responses.Response;
import ninja.soroosh.chatopia.core.runner.responses.TextResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
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
    public String webhook(@RequestBody TelegramRequest telegramRequest) throws IOException {
        var message = telegramRequest.getMessage() != null ? telegramRequest.getMessage() : telegramRequest.getCallbackQuery().getMessage();
        var commandText = telegramRequest.getCallbackQuery() == null ? message.getText() : telegramRequest.getCallbackQuery().getData();
        final long chatId = message.getChat().getId();
        final Command command = telegramCommandBuilder.build(commandText);
        final String sessionId = "telegram-" + chatId;
        final Response commandResponse = commandRunner.run(
                command,
                new Context(Optional.of(sessionId), "telegram")
        );

        Object response = null;
        if (commandResponse instanceof TextResponse textResponse) {
            var optionsMarkup = generateOptionsMark(textResponse.getOptions());
            response = restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendMessage", key),
                    new TelegramSendMessage(chatId, textResponse.getMessage(), optionsMarkup), Object.class);
        } else if (commandResponse instanceof PhotoResponse photoResponse) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("photo")
                    .filename("photo")
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

            HttpEntity<byte[]> fileEntity = new HttpEntity<>(photoResponse.getPhotoStream().readAllBytes(), fileMap);

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("photo", fileEntity);
            form.add("chat_id", chatId);
            form.add("caption", photoResponse.getCaption());
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(form, headers);

            response = restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendPhoto", key), requestEntity
                    , Object.class);
        }


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

