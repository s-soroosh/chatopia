package ninja.soroosh.chatopia.core.runner;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Response {
    private Response() {

    }

    private String message;
    List<Option> options;

    public static Response withMessage(String message) {
        var response = new Response();
        response.message = message;
        return response;
    }

    public Response withOptions(List<Option> options) {
        this.options = new ArrayList<>(options);
        return this;
    }

    public Response addOption(Option option) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }
        this.options.add(option);
        return this;
    }
}
