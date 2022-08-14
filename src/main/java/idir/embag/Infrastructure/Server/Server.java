package idir.embag.Infrastructure.Server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.Infrastructure.Server.Api.Requests.LoginRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.LoginResponse;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Types.Api.EHeaders.Headers;
import idir.embag.Types.Api.IApi;
import idir.embag.Types.Api.IApiWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Infrastructure.Server.IServer;

public class Server implements IServer{

    WebSocketImpl webSocketClient;

    String authToken;

    int apiVersion;

    String serverPath;

    int port;

    public Server(String serverPath,int port , String authToken,int apiVersion) {
        this.authToken = authToken;
        this.apiVersion = apiVersion;
        this.serverPath = serverPath;
        this.port = port;
    }
    

    private void login(IApiWrapper wrapper) {
        IApi loginApi = new LoginRequest(wrapper);

        loginApi.addHeader(Headers.access_token, authToken);

        Runnable initSocket = new Runnable() {
            @Override
            public void run() {
                try {
                    URI url = new URI("ws://"+serverPath+":"+port);
                    webSocketClient = new WebSocketImpl(url);
                    webSocketClient.connect();
                } catch (URISyntaxException e) {
                    System.out.println("Invalid server path");
                    e.printStackTrace();
                }
            }
        };

        LoginResponse loginHandler = new LoginResponse(initSocket);
        loginApi.setResponseHandler(loginHandler);

        loginApi.execute();
    }

    @Override
    public void dispatchApiCall(Map<EServerKeys,Object> data) {
        IApiWrapper wrapper = DataBundler.retrieveValue(data, EServerKeys.ApiWrapper);

        switch(wrapper.getApi()){
            case loginAdmin : login(wrapper);
            break;
            default : 
            break;
        }
    }

    
}
