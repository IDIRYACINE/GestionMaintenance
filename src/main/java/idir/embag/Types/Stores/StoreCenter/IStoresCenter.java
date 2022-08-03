package idir.embag.Types.Stores.StoreCenter;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public interface IStoresCenter {
    public void dispatch(StoreDispatch action);
    public void notify(StoreDispatch action);
    public void broadcast(StoreDispatch action);
    public void unsubscribeFromEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber);
    public void subscribeToEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber);
    public StoreDispatch createStoreEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data);

}
