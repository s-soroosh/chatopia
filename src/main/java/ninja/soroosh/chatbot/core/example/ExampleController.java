package ninja.soroosh.chatbot.core.example;

import ninja.soroosh.chatbot.core.runner.CommandRunner;
import ninja.soroosh.chatbot.core.runner.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @Autowired
    private CommandRunner commandRunner;

    @PostMapping(name = "/example")
    public ExampleResponse run(@RequestBody ExampleRequest exampleRequest) {
        // TODO: CommandBuilder
        final Response resp = commandRunner.run(() -> exampleRequest.getMessage());
        return new ExampleResponse(resp.message());
    }
}
