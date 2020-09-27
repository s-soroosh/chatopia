[![Codacy Badge](https://api.codacy.com/project/badge/Grade/44887b8a94d344b19f2f24f85228496d)](https://www.codacy.com/manual/soroosh.sarabadani/chatbot-core?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=psycho-ir/chatbot-core&amp;utm_campaign=Badge_Grade)

Chatopia
=======

A framework to build chatbot compatible with different platforms!
The idea is simple, develop chatbots in the same way do implement web applications via Spring Web.

A simple Example:

How to implement a chatbot that echo the message
------------------------------------------------
```java

public class MessageEchoExample {

    @OnCommand("echo")
    public Response onEchoCommand(String message, Context context) {
        return () -> "echo " + message;
    }
    //...
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

------  
to run the shell script called `set_telegram_webhook.sh` following steps might be needed:  
-  [ ]   install `jq`, jq is a lightweight and flexible command-line JSON processor:
-on Ubuntu:`sudo apt install jq`  
-on mac:`sudo brew install jq`
-doc and other options:[link](https://stedolan.github.io/jq/) 
       
-   [ ]   install `ngrok`:cross-platform application that enables developers to expose a local development server to the Internet with minimal effort.

-download `ngrok*.zip` from:[link](https://ngrok.com/download)
-unzip:unzip*.zip`
-log in to website
-set your token:`./ngrok authtoken <your_auth_token>`
-move it to bin:`sudo mv ngrok /usr/local/bin`
-check if everything works fine:`ngrok http 80`
