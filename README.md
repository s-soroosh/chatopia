Chatbot
=======

A framework to build chatbot compatible with different platforms!
The idea is simple, develop chatbots in the same way do implement web applications via Spring Web.

A simple Example:

## How to implement a chatbot that echo the message:
```java
@Service
@ChatController
public class MessageEchoExample {

    @OnCommand("echo")
    public Response onEchoCommand(String message, Context context) {
        return () -> "echo " + message;
    }
    ...
}
```

