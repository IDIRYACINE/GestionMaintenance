package idir.embag.EventStore.Stores.Generics;

public class StoreEvent {
    private EStoreEvents event;
    private Object data;
    private EStoreEventAction action;

    public StoreEvent(EStoreEvents event,EStoreEventAction action, Object data) {
        this.event = event;
        this.data = data;
        this.action = action;
    }

    public EStoreEvents getEvent() {
        return event;
    }

    public Object getData() {
        return data;
    }

    public EStoreEventAction getAction() {
        return action;
    }
    
    
}
