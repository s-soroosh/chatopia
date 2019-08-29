package ninja.soroosh.chatbot.core.example;

import ninja.soroosh.chatbot.core.annotation.ChatController;
import ninja.soroosh.chatbot.core.annotation.OnCommand;
import ninja.soroosh.chatbot.core.runner.Context;
import ninja.soroosh.chatbot.core.runner.Response;
import ninja.soroosh.chatbot.core.session.Session;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ChatController
public class MessageEchoExample {
    @OnCommand("echo")
    public Response onEchoCommand(String message, Context context) {
        return () -> "echo " + message;
    }

    @OnCommand("hi")
    public Response onHiCommand(String message, Context context) {
        final Session session = context.getSession().get();
        final Optional<String> previousCount = session.get("count");
        final String currentCount = previousCount.map(strCount -> String.valueOf(Integer.parseInt(strCount) + 1))
                .orElse("1");
        session.set("count", currentCount);

        return () -> "Hi man, How are you? you session Id is "
                + context.getSessionId() +
                " and you are on channel: " + context.getChannel() +
                " and you call me " + currentCount + " times";
    }
}
