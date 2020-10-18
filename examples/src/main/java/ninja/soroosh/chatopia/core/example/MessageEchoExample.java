package ninja.soroosh.chatopia.core.example;

import ninja.soroosh.chatopia.core.annotation.ChatController;
import ninja.soroosh.chatopia.core.annotation.OnCommand;
import ninja.soroosh.chatopia.core.runner.Context;
import ninja.soroosh.chatopia.core.runner.Response;
import ninja.soroosh.chatopia.core.session.Session;

import java.util.Optional;

@ChatController
public class MessageEchoExample {
    @OnCommand(value = "echo", help = "This command echo")
    public Response onEchoCommand(String message, Context context) {
        return () -> "your command is: echo"
                +"\n and your whole message is: " + message;
    }

    @OnCommand(value = "echo *", help = "This command echo")
    public Response onEchoStarCommand(String message, Context context) {
        return () -> "your command is: echo star, meaning that it differs from echo itself"
                +"\n and your whole message is: " + message;
    }

    @OnCommand(value = "hi", help = "start a chat")
    public Response onHiCommand(String message, Context context) {
        final Session session = context.getSession().get();
        final Optional<String> previousCount = session.get("count");
        final String currentCount = previousCount.map(strCount -> String.valueOf(Integer.parseInt(strCount) + 1))
                .orElse("1");
        session.set("count", currentCount);

        return () -> "Hi man, How are you? your session Id is "
                + context.getSessionId() +
                " and you are on channel: " + context.getChannel() +
                " and you call me " + currentCount + " times";
    }
}
