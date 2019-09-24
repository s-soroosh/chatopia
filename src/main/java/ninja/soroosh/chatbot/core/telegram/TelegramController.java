package ninja.soroosh.chatbot.core.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@RestController
public class TelegramController {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${chatops.connector.telegram.key}")
    private String key;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder.build();
    }


    @PostMapping(path = "/connectors/telegram")
    public String webhook(@RequestBody TelegramRequest telegramRequest) {
        final long chatId = telegramRequest.getMessage().getChat().getId();
        Object response = restTemplate.postForEntity(String.format("https://api.telegram.org/bot%s/sendMessage", key), new TelegramSendMessage(chatId, "echo"), Object.class);
        System.out.println(response);
        return "ok";
    }
}
