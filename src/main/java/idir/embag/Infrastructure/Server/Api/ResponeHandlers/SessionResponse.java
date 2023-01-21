package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.Serialisers.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DSessionResponse;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionRecordsWrapper;
import idir.embag.Types.Api.IApiResponseHandler;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
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

                if(parsedResponse.sessionId != null){
                    dispatchNavigateToActiveSession(parsedResponse);
                    dispatchActiveSessionExists(parsedResponse);
                    dispatchLoadingRecords();

                }

                    
                }
            catch (IOException e) {
                e.printStackTrace();
            }
            } 
        }
        
        private void dispatchActiveSessionExists(DSessionResponse responseData){
            StoreCenter storeCenter = StoreCenter.getInstance();

            Map<EEventsDataKeys,Object> data = new HashMap<>();
            data.put(EEventsDataKeys.Instance, responseData.sessionId);

            StoreDispatch event = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load, data);
            storeCenter.notify(event);


        }

        private void dispatchNavigateToActiveSession(DSessionResponse responseData){
            StoreCenter storeCenter = StoreCenter.getInstance();

            Map<EEventsDataKeys,Object> data = new HashMap<>();
            data.put(EEventsDataKeys.ApiResponse, responseData);

            StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.SessionEvent, EStoreEventAction.OpenSession, data);
            storeCenter.notify(event);


        }

        private void dispatchLoadingRecords(){
            int maxRetrivedRecord = 5000;
            int recordOffset = 0;

            ArrayList<Integer> permissions = AppState.getInstance().getCurrentUser().getDesignationsIds();

            Map<EServerKeys, Object> data = new HashMap<>();

            FetchActiveSessionRecordsWrapper apiWrapper = new FetchActiveSessionRecordsWrapper(maxRetrivedRecord,recordOffset,permissions);
            data.put(EServerKeys.ApiWrapper, apiWrapper);
    
            ServicesProvider.getInstance().getRemoteServer().dispatchApiCall(data);
        }

    }