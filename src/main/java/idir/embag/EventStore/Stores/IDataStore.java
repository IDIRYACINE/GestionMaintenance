package idir.embag.EventStore.Stores;

import idir.embag.EventStore.Stores.Generics.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.IEventSubscriber;
import idir.embag.EventStore.Stores.Generics.StoreEvent;

public interface IDataStore {
    public void add(StoreEvent event);
    public void remove(StoreEvent event);
    public void update(StoreEvent event);
    public void get(StoreEvent event);
    public void subscribe(IEventSubscriber subscriber,EStoreEvents store);
    public void unsubscribe(IEventSubscriber subscriber,EStoreEvents store);
}
