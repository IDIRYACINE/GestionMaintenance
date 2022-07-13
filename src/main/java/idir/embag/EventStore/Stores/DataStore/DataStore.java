package idir.embag.EventStore.Stores.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import idir.embag.EventStore.Stores.Generics.IEventSubscriber;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public class DataStore implements IDataStore {
    
    private Map<EStoreEvents,IDataDelegate> dataDelegates = new HashMap<>();
    private Map<EStoreEvents,ArrayList<IEventSubscriber>> subscribers = new HashMap<>();
    
    public DataStore(IDataDelegate stockDelegate, IDataDelegate inventoryDelegate, IDataDelegate historyDelegate,
            IDataDelegate familyDelegate) {

      
       dataDelegates.put(EStoreEvents.StockEvent, stockDelegate);
       dataDelegates.put(EStoreEvents.InventoryEvent, inventoryDelegate);
       dataDelegates.put(EStoreEvents.HistoryEvent, historyDelegate);
       dataDelegates.put(EStoreEvents.SessionWorkerEvent, familyDelegate);

       setupSubscribers();
       
    }

    @Override
    public void add(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.add(event.getData());
    }

    @Override
    public void remove(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.remove((int)event.getData());
        
    }

    @Override
    public void update(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.update((int)event.getData(), event.getData());
    }

    @Override
    public void get(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.search(event.getData());
        
    }

    @Override
    public void subscribe(IEventSubscriber subscriber, EStoreEvents store) {
        subscribers.get(store).add(subscriber);   
    }

    @Override
    public void unsubscribe(IEventSubscriber subscriber, EStoreEvents store) {
        subscribers.get(store).remove(subscriber);
    }

    private void setupSubscribers() {

        for(EStoreEvents event : EStoreEvents.values()) {
              subscribers.put(event, new ArrayList<IEventSubscriber>());
        }
        
    }

    @Override
    public void dispatch(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }
    
}
