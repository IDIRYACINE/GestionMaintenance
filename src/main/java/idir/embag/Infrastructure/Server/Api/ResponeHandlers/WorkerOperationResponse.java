package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DWorkerResponse;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Api.IApiResponseHandler;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import okhttp3.Response;

public class WorkerOperationResponse implements IApiResponseHandler{
    private EStoreEventAction action;

    @Override
    public void handleResponse(Response response) {
        if(response.code() == 200){
            try {
                String jsonBody = response.body().string();
                DWorkerResponse parsedResponse = GsonSerialiser.deserialise(jsonBody, DWorkerResponse.class);
                
                if(parsedResponse.operationExecutedSucessfully){
                    dispatchOperationExecutedSucessfully(parsedResponse);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

    private void dispatchOperationExecutedSucessfully(DWorkerResponse responseData){
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        data.put(EEventsDataKeys.ApiResponse, responseData);


        StoreCenter storeCenter = StoreCenter.getInstance();
        storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, action, data);
    }
    
}