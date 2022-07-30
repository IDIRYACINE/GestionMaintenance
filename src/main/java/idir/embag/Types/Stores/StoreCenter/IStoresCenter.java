package idir.embag.Types.Stores.StoreCenter;

import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public interface IStoresCenter {
    public void dispatch(StoreDispatch action);
    public void notify(StoreDispatch action);
    public void unsubscribeFromEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber);
    public void subscribeToEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber);

}
