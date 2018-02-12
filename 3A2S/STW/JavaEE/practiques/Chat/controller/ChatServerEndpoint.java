package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import model.Conversation;
import model.ConversationsManager;
import model.Message;
import model.Operator;
import model.OperatorsBean;
import model.Message.MessageCode;

@ServerEndpoint(value = "/chat/{room}", encoders= MessageEncoder.class, decoders= MessageDecoder.class) 
public class ChatServerEndpoint {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    @EJB private OperatorsBean operatorsBean;
    @EJB private ConversationsManager conversationsManager;
    
    @OnMessage
    public void handleMessage(Message msg, Session session) throws IOException, EncodeException {
        
        int channel = new Integer((String) session.getUserProperties().get("room"));
        Conversation conv = conversationsManager.getConversationById(channel);
        boolean hasToBeSent = false;
        int id;
        Operator op;
        
        if (conv != null) {
            System.out.println("INFO >>> MESSAGE SENT BY USER");
            op = conv.getOperator();
            if (op.getCurrentConvId() == -1) {
                op.setCurrentConvId(channel);   
            }
            id = op.getId();
            if(op.getCurrentConvId() == channel) {
                hasToBeSent = true;
            }
        } else {
            System.out.println("INFO >>> MESSAGE SENT BY OPERATOR");
            op = operatorsBean.getOperatorById(channel);
            conv = op.getCurrentConversation();
            id = op.getCurrentConvId();
            hasToBeSent = true;
        }
        
        if (hasToBeSent) {
            getSessionByChannel(id).getBasicRemote().sendObject(msg);
        }
        conv.addMessage(msg);
    }
    
    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        
        session.getUserProperties().put("room", room);
        sessions.add(session);
        int channel = new Integer((String) session.getUserProperties().get("room"));
        Operator op = operatorsBean.getOperatorById(channel);
        
        if (op != null) {
            System.out.println("INFO >>> OPERATOR SE HA CONECTADO");
            op.setSession(session);
        } else {
            System.out.println("INFO >>> USER SE HA CONECTADO");
            sendRefreshMsgIfUserOpenedASession(room);
        }
    }
    
    @OnClose
    public void onClose(Session session) {
       
        int roomId = new Integer((String) session.getUserProperties().get("room"));
        Conversation conv = conversationsManager.getConversationById(roomId);
        
        if (conv != null) {
            System.out.println("SE DESCONECTA UN USER");
            System.out.println("ELIMINANDO CONVERSACION DEL OPERATOR");
            
            Operator op = conv.getOperator();
            this.operatorsBean.getOperatorById(op.getId()).removeConversation(conv);
            this.conversationsManager.removeConversationById(roomId);
            
            if (op.getActiveChats().size() > 0) {
            	op.setCurrentConversation(op.getActiveChats().get(0));	
            	op.setCurrentConvId(op.getActiveChats().get(0).getConversationID());
            }
            
            //Message msg = new Message(MessageCode.CLOSE, ""+roomId, conv.getUserName());
            Message msg = new Message(MessageCode.CLOSE, "", "");
            
            try {
                op.getSession().getBasicRemote().sendObject(msg);
            } catch (IOException ex) {
                System.out.println("ERROR >>> IOEXCEPTION");
            } catch (EncodeException ex) {
                System.out.println("ERROR >>> ENCODEEXCEPTION");
            }
        } else {
            System.out.println("SE DECONECTA OPERATOR");
            int channel = new Integer((String) session.getUserProperties().get("room"));
            Operator op = operatorsBean.getOperatorById(channel);
            op.setOffline();
        }
        sessions.remove(session);
    }
    
    private void sendRefreshMsgIfUserOpenedASession(String room) {
        
        int operatorID = this.conversationsManager.getOperatorIdAssignedToAConversation(new Integer(room));
        Operator op = this.operatorsBean.getOperatorById(operatorID);
        try {
            //op.getSession().getBasicRemote().sendObject(new Message(MessageCode.REFRESH, ""+room, ""));
            op.getSession().getBasicRemote().sendObject(new Message(MessageCode.REFRESH, "", ""));
        } catch (IOException | EncodeException e) {
            System.out.println("ERROR >>> IOEXCEPTION | ENCODEEXCEPTION");
        }
    }
    
    private Session getSessionByChannel(int channelID) {
        for (Session s : sessions) {
            if (new Integer((String) s.getUserProperties().get("room")) == channelID) {
                return s;
            }
        }
        return null;
    }
}
