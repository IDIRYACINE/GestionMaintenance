package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;

import idir.embag.App;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DLoginResponse;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Types.Api.IApiResponseHandler;
import okhttp3.Response;

public class LoginResponse implements IApiResponseHandler{

    WebSocketImpl webSocket;

    @Override
    public void handleResponse(Response response) {
        if(response.code() == 200){
            try {
                String jsonBody = response.body().string();
                DLoginResponse parsedResponse = GsonSerialiser.deserialise(jsonBody, DLoginResponse.class);
                if(parsedResponse.isAutherised){
                    App.instance.loadApp();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

    public void setWebsocket(WebSocketImpl webSocket){
        this.webSocket = webSocket;
    }

    
}
