package idir.embag.Types.Stores.Generics.StoreEvent;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;

public class StoreEvent {
    private EStoreEvents event;
    private Map<EEventsDataKeys,Object> data;
    private EStoreEventAction action;

    public StoreEvent(EStoreEvents event,EStoreEventAction action, Map<EEventsDataKeys,Object> data) {
        this.event = event;
        this.data = data;
        this.action = action;
    }

    public EStoreEvents getEvent() {
        return event;
    }

    public Map<EEventsDataKeys,Object> getData() {
        return data;
    }

    public EStoreEventAction getAction() {
        return action;
    }
    
    
}
