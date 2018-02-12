package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="operatorchat")
@SessionScoped
public class OperatorChatBean {

	private int selectedConversationID;
	private String history;
	private String message;
	private List<ConversationDetails> conversations;
	
	public OperatorChatBean() {
		this.selectedConversationID = -1;
		this.history = "Welcome to the Chat\n";
		this.message = "";
		this.conversations = new ArrayList<ConversationDetails>();
	}

	public List<ConversationDetails> getConversations() {
		return conversations;
	}

	public void setConversations(List<ConversationDetails> conversations) {
		this.conversations = conversations;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = "Welcome to the Chat\n" + history;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addConversationDetails(ConversationDetails convD) {
		this.conversations.add(convD);
	}
	
	public void clearConversationDetails() {
		this.conversations.clear();
	}
	
	public class ConversationDetails {
		
		private String userName;
		private String product;
		private String userNameAndProduct;
		private int conversationID;
		private String conversationLinkStyle = NOT_SELECTED_CHAT_STYLE;
		private static final String NOT_SELECTED_CHAT_STYLE = "";
		private static final String SELECTED_CHAT_STYLE = "background-color:green;";
		
		public ConversationDetails(String userName, String product, int convID) {
			this.userName = userName;
			this.product = product;
			this.conversationID = convID;
			this.userNameAndProduct = this.userName + " - " + this.product;
			//this.conversationLinkStyle = NOT_SELECTED_CHAT_STYLE;
		}

		public String getUserName() {
			return userName;
		}

		public String getProduct() {
			return product;
		}

		public String getUserNameAndProduct() {
			return userNameAndProduct;
		}

		public int getConversationID() {
			return conversationID;
		}

		public String getConversationLinkStyle() {
			return conversationLinkStyle;
		}
		
		public void setConversationLinkStyleToSelectedChat() {
			this.conversationLinkStyle = SELECTED_CHAT_STYLE;
		}
		
		public void setConversationLinkStyleToUnSelectedChat() {
		    this.conversationLinkStyle = NOT_SELECTED_CHAT_STYLE;
		}
		
		@Override
        public String toString() {
           return this.userNameAndProduct; 
        }
	}
}
