package idir.embag.Infrastructure.Server.WebSocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebSocketImpl extends WebSocketClient{

    public WebSocketImpl(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("websocket connected");

    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("websocket closed" ) ;
        System.out.println("code : " + code);
        System.out.println("reason : " + reason);
        System.out.println("remote : " + remote);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
    
}
