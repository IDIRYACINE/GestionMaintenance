package idir.embag.Infrastructure.Server.WebSocket.ResponseHandlers;

import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class SessionRecordHandler {
    
    public void handleRecord(SessionRecord sessionRecord){
        SessionRecord[] values = {sessionRecord};
        EEventsDataKeys[] keys = {EEventsDataKeys.Instance};
        Map<EEventsDataKeys,Object> data = DataBundler.bundleData(keys,values);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Add, data);
        storeCenter.broadcast(action);
    }

    @SuppressWarnings({"unchecked"})
    public void handleRecordCollection(Collection<SessionRecord> recordCollection){
        Collection<SessionRecord>[] values = new Collection[1];
        values[0] = recordCollection;

        EEventsDataKeys[] keys = {EEventsDataKeys.InstanceCollection};
        Map<EEventsDataKeys,Object> data = DataBundler.bundleData(keys,values);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.AddCollection, data);
        storeCenter.broadcast(action);
    }
}
