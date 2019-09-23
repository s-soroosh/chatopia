package ninja.soroosh.chatbot.core.telegram;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramController {

    @PostMapping(path = "/connectors/telegram")
    public String webhook(@RequestBody TelegramRequest telegramRequest) {
        System.out.println(telegramRequest);
        return "ok";
    }
}
