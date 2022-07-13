package idir.embag.EventStore.Stores.Implementation;

import java.util.ArrayList;

import idir.embag.EventStore.Stores.IDataStore;
import idir.embag.EventStore.Stores.Generics.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.IEventSubscriber;
import idir.embag.EventStore.Stores.Generics.StoreEvent;

public class DataStore implements IDataStore {
    
    private ArrayList<IEventSubscriber> sessionSubscribers;
    private ArrayList<IEventSubscriber> workersSubscribers;
    private ArrayList<IEventSubscriber> inventorySubscriber;
    private ArrayList<IEventSubscriber> stockSubscribers;
    private ArrayList<IEventSubscriber> historySubscribers;



    @Override
    public void add(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void remove(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void get(StoreEvent event) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void subscribe(IEventSubscriber subscriber, EStoreEvents store) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void unsubscribe(IEventSubscriber subscriber, EStoreEvents store) {
        // TODO Auto-generated method stub
        
    }


    private void notifySubscribers(EStoreEvents event,Object data){
        
    }
    
}
