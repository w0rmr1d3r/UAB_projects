package model;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import controller.OperatorNotFoundException;

@Singleton
public class ConversationsManager {

    private static final int IMPOSSIBLE_NUM_OF_CONVERSATIONS = 5;
    @EJB private OperatorsBean operatorsBean;
    private HashMap<Integer, UserIdOperatorIdConversationTuple> conversationTuplesById;

    @PostConstruct
    private void init() {
        this.conversationTuplesById = new HashMap<Integer, UserIdOperatorIdConversationTuple>();
    }

    public void createConversation (
            int userId, 
            String category, 
            String product, 
            String subject, 
            String userName) throws OperatorNotFoundException {
        
        Operator op = getAvailableOperator();
        if(op != null){
            Conversation conv = new Conversation(userId, category, product, subject, userName);   
            //conv.setOperator(operatorsBean.getOperatorById(op.getId()));
            conv.setOperator(op);
            op.addConversation(conv);
            //op.setCurrentConversation(conv);
            conversationTuplesById.put(
                    conv.getConversationID(), 
                            new UserIdOperatorIdConversationTuple(
                                    conv.getConversationID(), 
                                    op.getId(),
                                            conv)
                            );
        } else {
            throw new OperatorNotFoundException("No existe el operador");
        }
    }
    
    private Operator getAvailableOperator() {
        for(Operator op : operatorsBean.getOperatorsList().values()) {
            if (op.isOnline() 
                    && op.getActiveChats().size() < IMPOSSIBLE_NUM_OF_CONVERSATIONS) {
                return op;
            }
        }
        return null;
    }
    
    public int getOperatorIdAssignedToAConversation(int id) {
        return this.conversationTuplesById.get(id).getOperatorId();
    }
    
    public Conversation getConversationById(int id) {
        try {
            return this.conversationTuplesById.get(id).getConversation();
        } catch (NullPointerException e) {
            return null;
        }
        
    }
    
    public void removeConversationById(int id) {
        this.conversationTuplesById.remove(new Integer(id));
    }
    
    private class UserIdOperatorIdConversationTuple {

        private int userId;
        private int operatorId;
        private Conversation conversation;
        
        public UserIdOperatorIdConversationTuple(
                int userId, 
                int operatorId, 
                Conversation conversation) {
            this.userId = userId;
            this.operatorId = operatorId;
            this.conversation = conversation;
        }
        
        public int getUserId() {
            return userId;
        }

        public int getOperatorId() {
            return operatorId;
        }

        public Conversation getConversation() {
            return conversation;
        }
    }
}
