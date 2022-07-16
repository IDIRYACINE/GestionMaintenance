package idir.embag.Types.Stores.Generics.StoreEvent;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;

public class StoreEvent {
    private EStoreEvents event;
    private Map<EEventDataKeys,Object> data;
    private EStoreEventAction action;

    public StoreEvent(EStoreEvents event,EStoreEventAction action, Map<EEventDataKeys,Object> data) {
        this.event = event;
        this.data = data;
        this.action = action;
    }

    public EStoreEvents getEvent() {
        return event;
    }

    public Map<EEventDataKeys,Object> getData() {
        return data;
    }

    public EStoreEventAction getAction() {
        return action;
    }
    
    
}
