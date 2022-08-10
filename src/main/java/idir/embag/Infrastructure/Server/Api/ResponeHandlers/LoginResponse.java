package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;

import idir.embag.App;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Types.Api.IApiResponseHandler;
import okhttp3.Response;

public class LoginResponse implements IApiResponseHandler{

    WebSocketImpl webSocket;

    @Override
    public void handleResponse(Response response) {
        if(response.code() == 200){
            try {
                App.instance.loadApp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWebsocket(WebSocketImpl webSocket){
        this.webSocket = webSocket;
    }
    
}
