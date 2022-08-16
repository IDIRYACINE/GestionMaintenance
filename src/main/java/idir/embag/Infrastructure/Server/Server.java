package idir.embag.Infrastructure.Server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.Infrastructure.Server.Api.Requests.CloseSessionRequest;
import idir.embag.Infrastructure.Server.Api.Requests.LoginRequest;
import idir.embag.Infrastructure.Server.Api.Requests.OpenSessionRequest;
import idir.embag.Infrastructure.Server.Api.Requests.RegisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.Requests.UnregisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.Requests.UpdateSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.LoginResponse;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.SessionResponse;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.WorkerOperationResponse;
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
    

    @Override
    public void dispatchApiCall(Map<EServerKeys,Object> data) {
        IApiWrapper apiWrapper = DataBundler.retrieveValue(data, EServerKeys.ApiWrapper);

        switch(apiWrapper.getApi()){
            case loginAdmin : login(apiWrapper);
            break;
            case closeSession : closeSession(apiWrapper);
            break;
            case openSession: openSession(apiWrapper);
            break;
            case registerSessionWorker: registerSessionWorker(apiWrapper);
            break;
            case unregisterSessionWorker: unregisterSessionWorker(apiWrapper);
            break;
            case updateSessionWorker: updateSessionWorker(apiWrapper);
            break;

            default : 
            break;
        }
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

    private void openSession(IApiWrapper apiWrapper){
        OpenSessionRequest request = new OpenSessionRequest(apiWrapper);
        request.addHeader(Headers.access_token, authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void closeSession(IApiWrapper apiWrapper){
        CloseSessionRequest request = new CloseSessionRequest(apiWrapper);
        request.addHeader(Headers.access_token, authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void registerSessionWorker(IApiWrapper apiWrapper){
        RegisterSessionWorkerRequest request = new RegisterSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void unregisterSessionWorker(IApiWrapper apiWrapper){
        UnregisterSessionWorkerRequest request = new UnregisterSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void updateSessionWorker(IApiWrapper apiWrapper){
        UpdateSessionWorkerRequest request = new UpdateSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }


    
}
