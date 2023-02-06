package idir.embag.Infrastructure.Server.WebSocket;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import idir.embag.App;
import idir.embag.Application.Utility.Serialisers.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DSocketMessage;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.SocketApisData.DReceiveRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.Server.WebSocket.ResponseHandlers.SessionRecordHandler;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;

public class WebSocketImpl extends WebSocketClient {

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
            displayPopupMessage("Error while loading application", null);
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        DSocketMessage sMessage = GsonSerialiser.deserialise(message, DSocketMessage.class);
        switch (sMessage.type) {
            case recordEvent:
                DReceiveRecord data = GsonSerialiser.deserialise(sMessage.data, DReceiveRecord.class);
                recordHandler.notfiyRecord(data);
                break;

            case recordCollectionEvent:
                // TODO implement tihs
                break;
            default:
                break;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("websocket closed");
        System.out.println("code : " + code);
        System.out.println("reason : " + reason);
        System.out.println("remote : " + remote);

        if(remote)
            displayPopupMessage("Connection to server lost, retry connecting?", this::retryConnection);
        else 
            displayPopupMessage("Couldn't connect to the server, retry connecting?", this::retryConnection);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    private void displayPopupMessage(String message, Consumer<Map<EEventsDataKeys, Object>> callback) {
        ConfirmationDialog dialogContent = new ConfirmationDialog();

        dialogContent.setMessage(message);

        dialogContent.setOnConfirm(callback);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.loadFxml();

        StoreCenter.getInstance().dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent,
                EStoreEventAction.Dialog, data);

    }

    private void retryConnection(Map<EEventsDataKeys, Object> data) {
        reconnect();
    }

}
