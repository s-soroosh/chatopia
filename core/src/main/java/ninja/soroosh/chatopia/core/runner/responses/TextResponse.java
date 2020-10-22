package ninja.soroosh.chatopia.core.runner.responses;

import lombok.Getter;
import ninja.soroosh.chatopia.core.runner.Option;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class TextResponse implements Response {
    TextResponse(String message) {
        this.message = message;
    }

    private String message;
    List<Option> options;

    public TextResponse withOptions(List<Option> options) {
        this.options = new ArrayList<>(options);
        return this;
    }

    public TextResponse addOption(Option option) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }
        this.options.add(option);
        return this;
    }
}
