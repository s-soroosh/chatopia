package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.RequiredArgsConstructor;
import ninja.soroosh.chatopia.core.runner.CallbackDataOption;
import ninja.soroosh.chatopia.core.runner.CallbackURLOption;
import ninja.soroosh.chatopia.core.runner.Option;
import ninja.soroosh.chatopia.core.runner.responses.PhotoResponse;
import ninja.soroosh.chatopia.core.runner.responses.Response;
import ninja.soroosh.chatopia.core.runner.responses.TextResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResponseHandler {

    private final RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;
    @Value("${chatopia.connector.telegram.key}")
    private String key;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder.build();
    }

    //    TODO: model a general handling return type
    public Object handle(TelegramMessage message, Response response) {
        Object result = null;
        final long chatId = message.getChat().getId();
        if (response instanceof TextResponse textResponse) {
            var optionsMarkup = generateOptionsMark(textResponse.getOptions());
            result = restTemplate.postForEntity(
                    String.format("https://api.telegram.org/bot%s/sendMessage", key),
                    new TelegramSendMessage(chatId, textResponse.getMessage(), optionsMarkup), Object.class);
        } else if (response instanceof PhotoResponse photoResponse) {
            if (photoResponse.getId() != null) {
                final var requestBody = new TelegramSendPhoto(chatId, photoResponse.getId(), photoResponse.getCaption());
                result = restTemplate.postForEntity(
                        String.format("https://api.telegram.org/bot%s/sendPhoto", key), requestBody
                        , Object.class);
            } else if (photoResponse.getUrl() != null) {
                final var requestBody = new TelegramSendPhoto(chatId, photoResponse.getUrl().toString(), photoResponse.getCaption());
                result = restTemplate.postForEntity(
                        String.format("https://api.telegram.org/bot%s/sendPhoto", key), requestBody
                        , Object.class);
            } else {
                // TODO: extract every response type to its handler
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
                ContentDisposition contentDisposition = ContentDisposition
                        .builder("form-data")
                        .name("photo")
                        .filename("photo")
                        .build();
                fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

                HttpEntity<byte[]> fileEntity = null;
                try {
                    fileEntity = new HttpEntity<>(photoResponse.getPhotoStream().readAllBytes(), fileMap);
                } catch (IOException e) {
                    // TODO: Handle error
                    e.printStackTrace();
                }

                MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
                form.add("photo", fileEntity);
                form.add("chat_id", chatId);
                form.add("caption", photoResponse.getCaption());
                HttpEntity<MultiValueMap<String, Object>> requestEntity
                        = new HttpEntity<>(form, headers);

                result = restTemplate.postForEntity(
                        String.format("https://api.telegram.org/bot%s/sendPhoto", key), requestEntity
                        , Object.class);
            }
        }
        return result;
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
