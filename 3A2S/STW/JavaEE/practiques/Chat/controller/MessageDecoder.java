package controller;

import model.Message;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String arg0) throws DecodeException {
       JsonObject jsono = Json.createReader(new StringReader(arg0)).readObject();
       return new Message(jsono.getInt("code"), jsono.getString("user"), jsono.getString("message"));
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
