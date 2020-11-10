package ninja.soroosh.chatopia.core.example;

import ninja.soroosh.chatopia.core.annotation.ChatController;
import ninja.soroosh.chatopia.core.annotation.OnCommand;
import ninja.soroosh.chatopia.core.annotation.OnEvent;
import ninja.soroosh.chatopia.core.runner.*;
import ninja.soroosh.chatopia.core.runner.responses.Response;
import ninja.soroosh.chatopia.core.session.Session;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@ChatController
public class MessageEchoExample {
    @OnCommand(value = "/echo", help = "This command echo")
    public Response onEchoCommand(Command command, Context context) {
        return Response.asText("echo " + command.name());
    }

    @OnCommand(value = "/echo *", help = "This command echo")
    public Response onEchoStarCommand(Command command, Context context) {
        return Response.asText(command.name().substring(5));
    }

    @OnCommand(value = "/hi", help = "start a chat")
    public Response onHiCommand(Command command, Context context) {
        final Session session = context.getSession().get();
        final Optional<String> previousCount = session.get("count");
        final String currentCount = previousCount.map(strCount -> String.valueOf(Integer.parseInt(strCount) + 1))
                .orElse("1");
        session.set("count", currentCount);

        return Response.asText("Hi, How are you " + command.commander().getName() +
                "?\nyour session Id is: "
                + context.getSessionId() +
                "\nyou are on channel: " + context.getChannel() +
                "\nyou have called me: " + currentCount + " times");
    }

    @OnCommand(value = "/options", help = "A showcase to list options")
    public Response onOptionsCommand(Command command, Context context) {
        return Response
                .asText("Here are the options")
                .withOptions(
                        List.of(
                                new CallbackDataOption("option1", "options"),
                                new CallbackURLOption("option2", "https://google.com")
                        )
                );
    }

    @OnCommand(value = "/photo", help = "A showcase to send a photo")
    public Response onPhotoCommand(Command command, Context context) {
        return Response.asPhoto(this.getClass().getClassLoader().getResourceAsStream("bird.jpg"));
    }

    @OnCommand(value = "/photo *", help = "Load photo as message in telegram")
    public Response onPhCommand(Command command, Context context) throws MalformedURLException {
        final var url = command.name().substring(6);
        return Response
                .asPhoto(url)
                .withCaption("chetoram?");
    }

    @OnCommand(value = "/video", help = "A showcase to send a video")
    public Response onVideoCommand(Command command, Context context) {
        return Response.asVideo(this.getClass().getClassLoader().getResourceAsStream("buongiorno.mp4"));
    }

    @OnCommand(value = "/kick *", help = "Kick a specific person")
    public Response onKick(Command command, Context context) {
        return null;
    }

    @OnEvent("NEW_CHAT_MEMBER")
    public Response onNewMember(Event<UserEventPayload> event, Context context) {
        return Response.asText("Khosh oomadi lanati %s!".formatted(event.getPayload().getFirstName()));
    }

    @OnEvent("NEW_CHAT_TITLE")
    public Response onNewChatTitle(Event<ChatEventPayload> event, Context context) {
        return Response.asText("Oooooof ajab esme khoobi roosh chat gozashti %s!".formatted(event.getPayload().getTitle()));
    }
}
