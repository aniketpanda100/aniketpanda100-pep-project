package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;

/**
 * Endpoints and handlers for the controller. The endpoints needed can be
 * found in readme.md as well as the test cases.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    /*
     * Constructor instantiates service objects
     */
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getAllUserMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler for posting accounts.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        Account account = ctx.bodyAsClass(Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(addedAccount);
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler for account logins.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        Account account = ctx.bodyAsClass(Account.class);
        Account foundAccount = accountService.login(account);
        if(foundAccount!=null){
            ctx.json(foundAccount);
        }else{
            ctx.status(401);
        }
    }

    /**
     * Handler for authenticating accounts.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        Message message = ctx.bodyAsClass(Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(addedMessage);
        }else{
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to retrieve all messages by a user.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getAllUserMessagesHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllUserMessages(id);
        ctx.json(messages);
    }

    /**
     * Handler to retrieve a message by its id.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void getMessageByIdHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if (message != null) ctx.json(message);
    }

    /**
     * Handler to delete a message by its id.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void deleteMessageByIdHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if (message != null) ctx.json(message);
    }

    /**
     * Handler to update a message by its id.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.put method.
     */
    private void updateMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        String text = ctx.body();
        text = text.substring(18,text.length()-3);

        System.out.println("MAP: " + text);
        Message message = messageService.updateMessage(id, text);

        if(message!=null){
            ctx.json(message);
        }else{
            ctx.status(400);
        }
    }

}