package idir.embag.Infrastructure.Server;

import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.Infrastructure.Server.Api.AsyncApiRequest;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.LoginWrapper;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.LoginResponse;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Types.Api.EHeaders;
import idir.embag.Types.Api.IApi;
import idir.embag.Types.Api.IApiWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Infrastructure.Server.IServer;

public class Server implements IServer{

    WebSocketImpl webSocketClient;

    String authToken;

    int apiVersion;

    String serverPath;

    public Server(String serverPath,String authToken,int apiVersion) {
        this.authToken = authToken;
        this.apiVersion = apiVersion;
        this.serverPath = serverPath;
    }
    

    private void login(IApiWrapper wrapper) {
        LoginWrapper loginWrapper = (LoginWrapper) wrapper;
        IApi loginApi = new AsyncApiRequest(wrapper);

        loginApi.addHeader(EHeaders.authToken, authToken);
        loginApi.addHeader(EHeaders.username, loginWrapper.getUsername());
        loginApi.addHeader(EHeaders.password, loginWrapper.getPassword());

        LoginResponse loginHandler = new LoginResponse();
        loginHandler.setWebsocket(webSocketClient);
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
