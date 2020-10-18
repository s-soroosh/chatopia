package ninja.soroosh.chatopia.core.runner;

import lombok.Data;

@Data
public class CallbackDataOption extends Option {
    public CallbackDataOption(String text, String data) {
        this.data = data;
        this.text = text;
    }

    private String data;
}
