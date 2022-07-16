package idir.embag.Types.Stores.Generics;

import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IEventSubscriber {
    public void notifyEvent(StoreEvent event);
    
}
