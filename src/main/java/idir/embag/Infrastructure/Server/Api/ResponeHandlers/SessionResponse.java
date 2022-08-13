package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import idir.embag.Application.Utility.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DSessionResponse;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Api.IApiResponseHandler;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import okhttp3.Response;

public class SessionResponse implements IApiResponseHandler{

    @Override
    public void handleResponse(Response response) {
        if(response.code() == 200){
            try {
                String jsonBody = response.body().string();
                DSessionResponse parsedResponse = GsonSerialiser.deserialise(jsonBody, DSessionResponse.class);
                
                if(parsedResponse.sessionId != -1){
                    dispatchActiveSessionExists(parsedResponse);
                }

                if(parsedResponse.records != null && parsedResponse.records.size() > 0){
                    dispatchLoadingRecords(parsedResponse.records);
                }
                    
                }
            catch (IOException e) {
                e.printStackTrace();
            }
            } 
        }
        
        private void dispatchActiveSessionExists(DSessionResponse responseData){
            Map<EEventsDataKeys,Object> data = new HashMap<>();
            data.put(EEventsDataKeys.ApiResponse, responseData);

            StoreCenter storeCenter = StoreCenter.getInstance();
            storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load, data);
        }

        private void dispatchLoadingRecords(List<SessionRecord> records){
            Map<EEventsDataKeys,Object> data = new HashMap<>();
            data.put(EEventsDataKeys.InstanceCollection, records);

            StoreCenter storeCenter = StoreCenter.getInstance();
            storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.AddCollection, data);

        }

    }