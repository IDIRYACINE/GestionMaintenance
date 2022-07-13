package idir.embag.EventStore.Stores.Generics;

public interface IEventSubscriber {
    public void notifyEvent(StoreEvent event);
    
}
