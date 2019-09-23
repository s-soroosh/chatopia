package ninja.soroosh.chatbot.core.example;

import ninja.soroosh.chatbot.core.runner.Command;
import ninja.soroosh.chatbot.core.runner.CommandRunner;
import ninja.soroosh.chatbot.core.runner.Context;
import ninja.soroosh.chatbot.core.runner.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ExampleController {
    @Autowired
    private CommandRunner commandRunner;
    @Autowired
    private ExampleCommandBuilder exampleCommandBuilder;

    @PostMapping("/example")
    public ExampleResponse run(@RequestBody ExampleRequest exampleRequest, @RequestHeader(value = "x-session-id", required = false) Optional<String> sessionId) {
        final Command command = exampleCommandBuilder.build(exampleRequest);
        final Context context = new Context(sessionId, "example");
        final Response resp = commandRunner.run(command, context);
        return new ExampleResponse(resp.message());
    }
}
