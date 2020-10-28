[ ![Download](https://api.bintray.com/packages/psycho-ir/chatopia/chatopia-core/images/download.svg) ](https://bintray.com/psycho-ir/chatopia/chatopia-core/_latestVersion)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/44887b8a94d344b19f2f24f85228496d)](https://www.codacy.com/manual/soroosh.sarabadani/chatbot-core?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=psycho-ir/chatbot-core&amp;utm_campaign=Badge_Grade)

Chatopia
======= 

A framework to build chatbot compatible with different platforms!
The idea is simple, develop chatbots in the same way do implement web applications via Spring Web.

A simple Example:

How to implement a chatbot able to echo the messages
------------------------------------------------
```java

public class MessageEchoExample {

    @OnCommand(value = "echo", help = "This command echo")
    public Response onEchoCommand(String message, Context context) {
        return Response.asText("echo " + message);
    }

    @OnCommand(value = "echo *", help = "This command echo")
    public Response onEchoStarCommand(String message, Context context) {
        return Response.asText(message.substring(5));
    }
    // space star at the end of command name lets arguments in
}
```
`@OnEvent` annotation case be used in order to perform an action based 
on events in the group. Example below demonstrates how to implement a welcome message for new members.
```java
    @OnEvent("NEW_CHAT_MEMBER")
    public Response onNewMember(Event<UserEventPayload> event, Context context) {
        return Response.asText("Khosh oomadi lanati %s!".formatted(event.getPayload().getFirstName()));
    }
```  

![alt text](Chatopia.png "Logo Title Text 1")

Milestones
----------
Below are some ideas I'll implement in the comming days, no promise to respect the order though!

-   [ ]  Design chatbot connector ready to implement for different platforms
-   [ ]  Telegram Chatbot connector 
-   [ ]  Google chat Chatbot connector 
-   [ ]  Slack chatbot connector
-   [ ]  Design plugin to make the chatbot extensible
-   [ ]  Some plugins out of the box
-   [ ]  List supported events

How to run examples
-----------
To test the example you need to run the shell script called `set_telegram_webhook.sh` in root folder
following steps might be needed:  
1.  Install `jq`. jq is a lightweight and flexible command-line JSON processor:  
	- on Ubuntu:`sudo apt install jq`  
	- on mac:`sudo brew install jq`  
	- doc and other options: [here](https://stedolan.github.io/jq/) 
  
2.  Install `ngrok`:cross-platform application that enables developers to expose a local development server to the Internet with minimal effort:  
	- download `ngrok*.zip` from: [here](https://ngrok.com/download)
	- unzip downloaded file: `unzip*.zip`
	- log in to website
	- set your token:`./ngrok authtoken <your_auth_token>`
	- move it to bin:`sudo mv ngrok /usr/local/bin`
	- check if everything works fine by this command:`ngrok http 80`  
  
3. Get your currently existing bot API from telegrams [botfather](www.T.me/BotFather) or create one.  

4. Once you have your API Key ready, run this script. Replace TELEGRAM_API_KEY with your API Key:
	- set API key in: `resources/application.yml`  
	- `./set_telegram_webhook.sh 8080 /connectors/telegram TELEGRAM_API_KEY`

5. If everything is fine you will see:  
	`{"ok":true,"result":true,"description":"Webhook is already set"}`  
6. Send a simple `hi` message to your bot to check if its working. have fun.
