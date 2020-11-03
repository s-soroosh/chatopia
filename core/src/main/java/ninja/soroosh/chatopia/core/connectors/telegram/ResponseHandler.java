package ninja.soroosh.chatopia.core.connectors.telegram;

import lombok.RequiredArgsConstructor;
import ninja.soroosh.chatopia.core.runner.CallbackDataOption;
import ninja.soroosh.chatopia.core.runner.CallbackURLOption;
import ninja.soroosh.chatopia.core.runner.Option;
import ninja.soroosh.chatopia.core.runner.responses.PhotoResponse;
import ninja.soroosh.chatopia.core.runner.responses.Response;
import ninja.soroosh.chatopia.core.runner.responses.TextResponse;
import ninja.soroosh.chatopia.core.runner.responses.VideoResponse;
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

    public static final String BASEURL = "https://api.telegram.org/bot";
    public static final String SEND_MESSAGE = "sendMessage";
    public static final String SEND_PHOTO = "sendPhoto";
    public static final String SEND_VIDEO = "sendVideo";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_PHOTO = "photo";
    private long chatId;
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
                    BASEURL.concat(key).concat("/").concat(SEND_MESSAGE),
                    new TelegramSendMessage(chatId, textResponse.getMessage(), optionsMarkup), Object.class);
        } else if (response instanceof PhotoResponse || response instanceof VideoResponse) {
            result = MediaResponseTemplate(response);
        }
        return result;
    }

    private Object MediaResponseTemplate(Response response) {
        Object result = null;
        if (response instanceof PhotoResponse photoResponse) {
            if (photoResponse.getId() != null) {
                final var requestBody = new TelegramSendMedia(chatId, photoResponse.getId(), photoResponse.getCaption());
                result = restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_PHOTO), requestBody
                        , Object.class);
            } else if (photoResponse.getUrl() != null) {
                final var requestBody = new TelegramSendMedia(chatId, photoResponse.getUrl().toString(), photoResponse.getCaption());
                result = restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_PHOTO), requestBody
                        , Object.class);
            } else {
                HttpEntity<MultiValueMap<String, Object>> requestEntity
                        = generateForm(response, TYPE_PHOTO);
                return restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_PHOTO), requestEntity
                        , Object.class);
            }
        } else if (response instanceof VideoResponse videoResponse) {
            if (videoResponse.getId() != null) {
                final var requestBody = new TelegramSendMedia(chatId, videoResponse.getId(), videoResponse.getCaption());
                result = restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_VIDEO), requestBody
                        , Object.class);
            } else if (videoResponse.getUrl() != null) {
                final var requestBody = new TelegramSendMedia(chatId, videoResponse.getUrl().toString(), videoResponse.getCaption());
                result = restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_VIDEO), requestBody
                        , Object.class);
            } else {
                HttpEntity<MultiValueMap<String, Object>> requestEntity
                        = generateForm(response, TYPE_VIDEO);
                return restTemplate.postForEntity(
                        BASEURL.concat(key).concat("/").concat(SEND_VIDEO), requestEntity
                        , Object.class);
            }
        }
        return result;
    }

    private HttpEntity<MultiValueMap<String, Object>> generateForm(Response response, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name(type)
                .filename(type)
                .build();

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        HttpEntity<byte[]> fileEntity = null;
        try {
            fileEntity = new HttpEntity<>(response.getStream().readAllBytes(), fileMap);
        } catch (IOException e) {
            // TODO: Handle error
            e.printStackTrace();
        }

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add(type, fileEntity);
        form.add("chat_id", chatId);
        form.add("caption", response.getCaption());
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(form, headers);
        return requestEntity;
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
