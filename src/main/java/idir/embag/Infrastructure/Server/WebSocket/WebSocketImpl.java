package idir.embag.Infrastructure.Server.WebSocket;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import idir.embag.App;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DSocketMessage;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Infrastructure.Server.WebSocket.ResponseHandlers.SessionRecordHandler;

public class WebSocketImpl extends WebSocketClient{

    private final String recordEvent = "onSessionRecord";
    private final static String recordCollectionEvent = "onSessionRecordCollection";

    private SessionRecordHandler recordHandler;

    public WebSocketImpl(URI serverUri) {
        super(serverUri);
        recordHandler = new SessionRecordHandler();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("websocket connected");
        try {
            App.instance.loadApp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void onMessage(String message) {
        DSocketMessage sMessage = GsonSerialiser.deserialise(message, DSocketMessage.class);
        switch(sMessage.type){
            case recordEvent :
                recordHandler.handleRecord((SessionRecord) sMessage.data);
            break;

            case recordCollectionEvent:
                recordHandler.handleRecordCollection((Collection<SessionRecord>) sMessage.data);
            break;

            default :
            break;
        }
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
