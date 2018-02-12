package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.websocket.Session;

public class Operator {
    
    private boolean online;
    private String welcomeMessage;
    private String name;
    private String login;
    private String passwd;
    private int id;
    private int currentConvId;
    private String department;
    private String pictureSrc;
    private Conversation currentConversation;
    private List<Conversation> activeChats;
    private Hashtable<Integer, Conversation> conversationsById;
    private Session session;
    
    public Operator(int id, String login, String passwd) {
        this.id = id;
        this.login = login;
        this.passwd = passwd;
        this.currentConversation = null;
        this.activeChats = new ArrayList<Conversation>();
        this.conversationsById = new Hashtable<Integer, Conversation>();
        this.session = null;
        this.currentConvId = -1;
    }

    public Operator(int id, String name, String login, String passwd, String welcomeMsg) {
        this(id, login, passwd);
        this.name = name;
        this.welcomeMessage = welcomeMsg;  
    }
    
    public Operator(int id, String name, String login, String passwd, String department, String welcomeMsg) {
        this(id, name, login, passwd, welcomeMsg);
        this.department = department; 
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline() {
        this.online = true;
    }
    
    public void setOffline() {
        this.online = false;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    public int getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }
    
    public String getPictureSrc() {
        return this.pictureSrc;
    }
    
    public int getCurrentConvId() {
        return currentConvId;
    }

    public void setCurrentConvId(int currentConvId) {
        this.currentConvId = currentConvId;
    }

    public Conversation getCurrentConversation() {
        return currentConversation;
    }

    public void setCurrentConversation(Conversation currentConversation) {
        this.currentConversation = currentConversation;
    }
    
    public List<Conversation> getActiveChats() {
        return activeChats;
    }
    
    public void addConversation(Conversation conversation) {
        this.activeChats.add(conversation);
        if (this.currentConvId == -1) {
            setCurrentConvId(conversation.getConversationID());
            setCurrentConversation(conversation);
        }
    }
    
    public int getNumberOfActiveChats() {
        return this.activeChats.size();
    }
    
    public Conversation getAConversationById(int convID) {
        for (Conversation conv : this.activeChats) {
            if (conv.getConversationID() == convID) {
                return conv;
            }
        }
        return null;
    }
    
    public void removeConversation(Conversation conv) {
        //this.conversationsById.remove(convID);
        this.activeChats.remove(conv);
    }
    
    public boolean hasActiveConversations() {
        return (this.activeChats.size() > 0);
    }
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}   
