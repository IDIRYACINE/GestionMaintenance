package idir.embag.Types.Stores.Generics;

import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IStore {
    public void dispatch(StoreEvent event);
    public void notifySubscriber(StoreEvent event);
    public void broadcast(StoreEvent event);
    public void subscribe(StoreEvent event);
    public void unsubscribe(StoreEvent event);
}
