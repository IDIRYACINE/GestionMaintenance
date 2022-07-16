package idir.embag.Types.Stores.Generics.StoreDispatch;

import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class StoreDispatch {
    private EStores store;
    private StoreEvent event;

    public StoreDispatch(EStores store, StoreEvent event) {
        this.store = store;
        this.event = event;
    }

    public EStores getStore() {
        return store;
    }

    public StoreEvent getEvent() {
        return event;
    }
    
    
}
