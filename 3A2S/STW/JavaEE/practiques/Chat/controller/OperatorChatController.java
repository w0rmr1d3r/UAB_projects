package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import model.Conversation;
import model.Operator;
import model.OperatorsBean;
import model.Message;

import view.OperatorChatBean;
import view.OperatorChatBean.ConversationDetails;

@ManagedBean(name="operatorchatcontroller")
@SessionScoped
public class OperatorChatController {

    private Operator operator;
    private List<Conversation> conversations;
    private int currentConversationID;
    private static final String PUSH_CHANNEL = "NOT DEFINED";
    @ManagedProperty(value="#{operatorchat}") OperatorChatBean ocb;
    @EJB OperatorsBean ob;
    
    public OperatorChatController() {
        this.operator = null;
        this.conversations = new ArrayList<Conversation>();
        this.currentConversationID = -1;
    }
    
    public void setOcb(OperatorChatBean ocb) {
        this.ocb = ocb;
    }
    
    public void setOperator(Operator op) {
        this.operator = op;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
    private void refreshConversationArea(Conversation conversation) {
        setupCurrentConversationID();
        String history = "";
        for (Message m : conversation.getOrderedMessages()) {
        	history += m.toString() + "\n";
        }
        ocb.setHistory(history);
    }
    
    private void changeToConversation(int convID) {
        this.currentConversationID = convID;
        for (ConversationDetails convD : this.ocb.getConversations()) {
            this.changeToConversation(convD);
        }
    }
    
    public void changeToConversation(ConversationDetails convDetails) {
        System.out.println("INFO >>> OCC CHANGING CONVERSATION DETAILS");
        
        ConversationDetails currentConvDetails = getConversationDetailsById(this.currentConversationID);
        
        for (ConversationDetails convD : this.ocb.getConversations()) {
            convD.setConversationLinkStyleToUnSelectedChat();
        }
        
        this.currentConversationID = convDetails.getConversationID();
        Conversation conv = this.ob.getOperatorById(this.operator.getId()).getAConversationById(this.currentConversationID);
        this.ob.getOperatorById(this.operator.getId()).setCurrentConversation(conv);
        this.ob.getOperatorById(this.operator.getId()).setCurrentConvId(conv.getConversationID());
        currentConvDetails.setConversationLinkStyleToUnSelectedChat();
        convDetails.setConversationLinkStyleToSelectedChat();
        this.refreshConversationArea(conv);
    }
    
    public ConversationDetails getConversationDetailsById(int convID) {
    	for (ConversationDetails cd : this.ocb.getConversations()) {
    		if (cd.getConversationID() == convID) {
    			return cd;
    		}
    	}
    	return null;
    }
    
    public void refreshConversations() {
        System.out.println("INFO >>> OCC REFRESHING CONVERSATIONS");
        this.setupCurrentConversationID();
        ocb.clearConversationDetails();
        
        for (Conversation conv : this.ob.getOperatorById(this.operator.getId()).getActiveChats()) {
        	ConversationDetails convD = ocb.new ConversationDetails(conv.getUserName(), conv.getProduct(), conv.getConversationID());
        	ocb.addConversationDetails(convD);
        	System.out.println("CONVERSATION " + conv.getConversationID() + " ADDED!");
        }
        
        if (!ocb.getConversations().isEmpty()) {
            for (ConversationDetails convD : this.ocb.getConversations()) {
                if (convD.getConversationID() == this.currentConversationID) {
                    convD.setConversationLinkStyleToSelectedChat();
                } else {
                    convD.setConversationLinkStyleToUnSelectedChat();
                }
            }
            this.refreshConversationArea(this.ob.getOperatorById(this.operator.getId()).getCurrentConversation());
        } else {
        	System.out.println("INFO >>> OPERATOR NO TIENE CONVERSAS ACTIVAS");
        	this.ocb.setHistory("");
        	this.ob.getOperatorById(this.operator.getId()).setCurrentConversation(null);
        	this.ob.getOperatorById(this.operator.getId()).setCurrentConvId(-1);
        }
    }
    
    private void setupCurrentConversationID() {
        if (this.ob.getOperatorById(this.operator.getId()).getCurrentConversation() != null) {
            this.currentConversationID = this.ob.getOperatorById(this.operator.getId()).getCurrentConversation().getConversationID();
        }
    }
    
    public void removeConversation(int ID) {
        System.out.println("INFO >>> OCC REMOVE CONV");
        for (Conversation conv : this.ob.getOperatorById(this.operator.getId()).getActiveChats()) {
            if (conv.getConversationID() == ID) {
            	this.ob.getOperatorById(this.operator.getId()).getActiveChats().remove(conv);
            }
        }
        this.refreshConversations();
    }
    
    public boolean isLogged() {
    	return !this.ob.getOperatorById(this.operator.getId()).isOnline();
    	
    }
    public boolean disableSendButton() {
    	return !this.isAllowedToLogout();
    }
    
    public boolean isAllowedToLogout() {
        System.out.println("INFO >>> OCC allowedtologout");
        return (this.ob.getOperatorById(this.operator.getId()).getActiveChats().size() > 0 && !this.isLogged());
    }
}
