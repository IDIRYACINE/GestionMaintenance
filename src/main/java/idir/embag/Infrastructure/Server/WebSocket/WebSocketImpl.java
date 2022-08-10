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
        
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
        
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
    
}
