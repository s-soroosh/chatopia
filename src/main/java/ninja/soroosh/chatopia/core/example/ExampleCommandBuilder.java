package ninja.soroosh.chatopia.core.example;

import ninja.soroosh.chatopia.core.runner.Command;
import ninja.soroosh.chatopia.core.runner.CommandBuilder;
import org.springframework.stereotype.Service;

@Service
public class ExampleCommandBuilder implements CommandBuilder<ExampleRequest> {
    @Override
    public Command build(ExampleRequest request) {
        return () -> request.getMessage();
    }
}
