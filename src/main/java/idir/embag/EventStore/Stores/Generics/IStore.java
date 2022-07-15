package idir.embag.EventStore.Stores.Generics;

import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface IStore {
    public void dispatch(StoreEvent event);
    public void notifySubscribers(StoreEvent event);
    public void subscribe(StoreEvent event);
    public void unsubscribe(StoreEvent event);
}
