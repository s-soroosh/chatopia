package ninja.soroosh.chatopia.core.runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallbackURLOption extends Option {
    public CallbackURLOption(String text, String url) {
        this.url = url;
        this.text = text;
    }

    private String url;
}
