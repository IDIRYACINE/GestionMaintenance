package idir.embag.Infrastructure.Server.Api.ResponeHandlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.Utility.Serialisers.GsonSerialiser;
import idir.embag.DataModels.ApiBodyResponses.DSessionResponse;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Api.IApiResponseHandler;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import okhttp3.Response;

public class RecordsResponse implements IApiResponseHandler {

    @Override
    public void handleResponse(Response response) {
        if (response.code() == 200) {
            try {

                String jsonBody = response.body().string();
                DSessionResponse parsedResponse = GsonSerialiser.deserialise(jsonBody, DSessionResponse.class);

                if (parsedResponse.records != null) {
                    dispatchLoadSessionRecords(parsedResponse);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatchLoadSessionRecords(DSessionResponse responseData) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        data.put(EEventsDataKeys.InstanceCollection, responseData.records);


        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionEvent,
                EStoreEventAction.AddCollection, data);
        storeCenter.notify(event);
    }
}
