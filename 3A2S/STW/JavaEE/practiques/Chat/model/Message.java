package model;

public class Message {

    private String message;
    private String from;
    private int timeStamp;
    private MessageCode code;
    
    public Message(String from, String message) {
        this.message = message;
        this.from = from;
        this.code = MessageCode.MESSAGE;
        this.timeStamp = -1;
    }
    
    public Message(int timeStamp, String from, String message) {
        this(from, message);
        this.timeStamp = timeStamp;
    }
    
    public Message(MessageCode msgCode, String from, String message) {
        this(from, message);
        this.code = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public int getTimeStamp() {
        return timeStamp;
    }
    
    public MessageCode getCode() {
        return code;
    }
    
    public void setCode(MessageCode code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return this.from + "> " + this.message;
    }
    
    public enum MessageCode {
        
        MESSAGE (0), REFRESH (1), CLOSE(2);
        private int intCode;
        
        private MessageCode(int code) {
            this.intCode = code;
        }
        
        public int intValue() {
            return intCode;
        }
    }
}
