package controller;

import model.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>{

    @Override
    public String encode(Message arg0) throws EncodeException {
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        
        builder.add("code", arg0.getCode().toString())
               .add("user", arg0.getFrom())
               .add("message", arg0.getMessage());
        
        JsonObject ob = builder.build();
         
        return ob.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }  
}
