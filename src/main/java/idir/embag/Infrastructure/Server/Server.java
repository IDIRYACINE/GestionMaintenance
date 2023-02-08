package idir.embag.Infrastructure.Server;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.Infrastructure.Server.Api.ServerConfigurations;
import idir.embag.Infrastructure.Server.Api.Requests.CloseSessionRequest;
import idir.embag.Infrastructure.Server.Api.Requests.FetchActiveSessionRecordsRequest;
import idir.embag.Infrastructure.Server.Api.Requests.FetchActiveSessionRequest;
import idir.embag.Infrastructure.Server.Api.Requests.LoginRequest;
import idir.embag.Infrastructure.Server.Api.Requests.OpenSessionRequest;
import idir.embag.Infrastructure.Server.Api.Requests.RegisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.Requests.UnregisterSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.Requests.UpdateSessionWorkerRequest;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.LoginResponse;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.RecordsResponse;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.SessionResponse;
import idir.embag.Infrastructure.Server.Api.ResponeHandlers.WorkerOperationResponse;
import idir.embag.Infrastructure.Server.WebSocket.WebSocketImpl;
import idir.embag.Types.Api.EHeaders.Headers;
import idir.embag.Types.Api.EHeaders;
import idir.embag.Types.Api.IApi;
import idir.embag.Types.Api.IApiWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Infrastructure.Server.IServer;

import java.util.concurrent.Future;
import java.util.function.BiFunction;
import idir.embag.Infrastructure.ServiceProvider.Service;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.SearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchAlgorithm;
import idir.embag.Infrastructure.ServiceProvider.Algorithms.BinarySearch.BinarySearchComparator;
import idir.embag.Infrastructure.ServiceProvider.Events.Command;
import idir.embag.Infrastructure.ServiceProvider.Events.RawServiceEventData;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEvent;
import idir.embag.Infrastructure.ServiceProvider.Events.ServiceEventResponse;
import idir.embag.Infrastructure.ServiceProvider.Types.CommandSearchAlgorithm;

@SuppressWarnings({ "rawtypes"})
public class Server extends Service implements IServer  {

    WebSocketImpl webSocketClient;

   ServerConfigurations serverConfigurations;

    private Server(CommandSearchAlgorithm searchAlgorithm) {
        super(searchAlgorithm);
        registerDefaultCommand();
    }


    private static Server instance;

    public static Server getInstance() {
        return instance;
    }

    public static Server getInstance(ServerConfigurations serverConfigurations) {
        if (instance == null) {
            CommandSearchAlgorithm searchAlgorithm = (CommandSearchAlgorithm) ((SearchAlgorithm) createSearchAlgorithm());

            instance = new Server(searchAlgorithm);
        }
        return instance;
    }


    private static BinarySearchAlgorithm<Command, Integer> createSearchAlgorithm() {
        BinarySearchAlgorithm<Command, Integer> searchAlgorithm = new BinarySearchAlgorithm<Command, Integer>();
        
        BiFunction<Command, Integer,Boolean> isGreaterThan = (command, id) -> command.commandId > id;
        BiFunction<Command, Integer,Boolean> isLessThan = (command, id) -> command.commandId < id;

        BinarySearchComparator<Command, Integer> comparator = new BinarySearchComparator<Command, Integer>(isGreaterThan,isLessThan);

        searchAlgorithm.setComparator(comparator);
        
        return searchAlgorithm;
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
            case fetchActiveSession : fetchActiveSession(apiWrapper);
            break; 
            case updateSessionWorker: updateSessionWorker(apiWrapper);
            break;

            case fetchActiveSessionRecords : fetchRecords(apiWrapper);
            break;

            default : 
            break;
        }
    }

    private void login(IApiWrapper wrapper) {
        IApi loginApi = new LoginRequest(wrapper);

        loginApi.addHeader(Headers.access_token, serverConfigurations.authToken);

        Runnable initSocket = new Runnable() {
            @Override
            public void run() {
                try {
                    URI url = new URI("ws://"+serverConfigurations.serverPath+":"+serverConfigurations.port);
                    webSocketClient = new WebSocketImpl(url);
                    webSocketClient.addHeader(EHeaders.valueOf(Headers.access_token), serverConfigurations.authToken);
                    webSocketClient.connectBlocking();

                } catch (URISyntaxException | InterruptedException e) {
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
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void closeSession(IApiWrapper apiWrapper){
        CloseSessionRequest request = new CloseSessionRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void registerSessionWorker(IApiWrapper apiWrapper){
        RegisterSessionWorkerRequest request = new RegisterSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void unregisterSessionWorker(IApiWrapper apiWrapper){
        UnregisterSessionWorkerRequest request = new UnregisterSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }

    private void updateSessionWorker(IApiWrapper apiWrapper){
        UpdateSessionWorkerRequest request = new UpdateSessionWorkerRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        WorkerOperationResponse responseHandler = new WorkerOperationResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }


    private void fetchActiveSession(IApiWrapper apiWrapper){
       
        FetchActiveSessionRequest request = new FetchActiveSessionRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        SessionResponse responseHandler = new SessionResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }
    
    private void fetchRecords(IApiWrapper apiWrapper){
        FetchActiveSessionRecordsRequest request = new FetchActiveSessionRecordsRequest(apiWrapper);
        request.addHeader(Headers.access_token, serverConfigurations.authToken);

        RecordsResponse responseHandler = new RecordsResponse();
        request.setResponseHandler(responseHandler);
        request.execute();
    }





    private void registerDefaultCommand() {

    }

    @Override
    public void onEventForCallback(ServiceEvent event) {
        
    }


    @Override
    public Future<ServiceEventResponse> onEventForResponse(ServiceEvent event) {
        return null;
    }


    @Override
    public Future<ServiceEventResponse> onRawEvent(RawServiceEventData event) {
        return null;
    }
}
