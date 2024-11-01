package Service;

import Model.Account;
import Model.Message;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;


    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();
    }

    /**
     * Uses the MessageDAO to persist a message. The given Message will not have an id provided.
     *
     * @param message an message object.
     * @return The persisted message if the persistence is successful.
     */
    public Message addMessage(Message message) {
        String text = message.getMessage_text();
        if (text == null || text.length() < 1 || text.length() > 255) return null;
        if (accountDAO.getAccountById(message.getPosted_by()) == null) return null;
        return messageDAO.insertMessage(message);
    }

    /**
     * Use the MessageDAO to retrieve all messages.
     *
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Use the MessageDAO to retrieve all messages by a user.
     *
     * @return all messages by a user
     */
    public List<Message> getAllUserMessages(int id) {
        return messageDAO.getAllUserMessages(id);
    }

    /**
     * Use the MessageDAO to retrieve message by its id.
     *
     * @return message by id
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * Use the MessageDAO to delete message by its id.
     *
     * @return message by id
     */
    public Message deleteMessageById(int id) {
        Message message = getMessageById(id);
        if (! messageDAO.deleteMessageById(id)) return null;
        return message;
    }

    /**
     * Use the MessageDAO to update message by its id.
     *
     * @return updated message by id
     */
    public Message updateMessage(int id, String text) {
        if (text == null || text.length() < 1 || text.length() > 255) return null;
        if (messageDAO.getMessageById(id) == null) return null;
        if(! messageDAO.updateMessage(id, text)) return null;

        return getMessageById(id);
    }
  
}
