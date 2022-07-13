package idir.embag.EventStore.Stores.Generics;

import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface IEventSubscriber {
    public void notifyEvent(StoreEvent event);
    
}
