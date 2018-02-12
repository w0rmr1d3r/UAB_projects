package model;

import java.util.ArrayList;

public class Conversation {

    private String category;
    private String product;
    private String subject;
    private String userName;
    private int conversationID;
    private ArrayList<Message> orderedMessages;
    private Operator operator;

    public Conversation(int id) {
        this.conversationID = id;
        this.orderedMessages = new ArrayList<Message>();
    }
    
    public Conversation(
            int id, 
            String category, 
            String product, 
            String subject, 
            String userName) {
        this(id);
        this.category = category;
        this.product = product;
        this.subject = subject;
        this.userName = userName;
    }
    
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getProduct() {
        return product;
    }

    public String getUserName() {
        return userName;
    }

    public int getConversationID() {
        return conversationID;
    }
    
    public void addMessage(Message msg) {
        this.orderedMessages.add(msg);
    }
    
    public ArrayList<Message> getOrderedMessages() {
        return this.orderedMessages;
    }
    
    @Override
    public String toString() {
        return this.category + ", " + this.product;
    }
}
