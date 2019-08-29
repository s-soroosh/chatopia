package ninja.soroosh.chatbot.core.example;

import ninja.soroosh.chatbot.core.runner.Command;
import ninja.soroosh.chatbot.core.runner.CommandBuilder;
import org.springframework.stereotype.Service;

@Service
public class ExampleCommandBuilder implements CommandBuilder<ExampleRequest> {
    @Override
    public Command build(ExampleRequest request) {
        return () -> request.getMessage();
    }
}
