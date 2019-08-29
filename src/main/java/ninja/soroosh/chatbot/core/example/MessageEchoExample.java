package ninja.soroosh.chatbot.core.example;

import ninja.soroosh.chatbot.core.annotation.ChatController;
import ninja.soroosh.chatbot.core.annotation.OnCommand;
import ninja.soroosh.chatbot.core.runner.Response;
import org.springframework.stereotype.Service;

@Service
@ChatController
public class MessageEchoExample {
    @OnCommand("echo")
    public Response onEchoCommand(String message) {
        return () -> "echo " + message;
    }

    @OnCommand("hi")
    public Response onHiCommand(String message) {
        return () -> "Hi man, How are you?";
    }
}
