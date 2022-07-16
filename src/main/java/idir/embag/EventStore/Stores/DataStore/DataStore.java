package idir.embag.EventStore.Stores.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.DataStore.IDataStore;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class DataStore implements IDataStore {
    
    private Map<EStoreEvents,IDataDelegate> dataDelegates = new HashMap<>();
    
    private Map<EStoreEvents,ArrayList<IEventSubscriber>> subscribers = new HashMap<>();

    private Map<EStoreEventAction , Consumer<StoreEvent>> actions = new HashMap<>();
       
    public DataStore(IDataDelegate stockDelegate, IDataDelegate inventoryDelegate, IDataDelegate historyDelegate,
            IDataDelegate familyDelegate) {

       dataDelegates.put(EStoreEvents.StockEvent, stockDelegate);
       dataDelegates.put(EStoreEvents.InventoryEvent, inventoryDelegate);
       dataDelegates.put(EStoreEvents.HistoryEvent, historyDelegate);
       dataDelegates.put(EStoreEvents.SessionWorkerEvent, familyDelegate);

       setupSubscribers();
       setupActions();
       
    }

    @Override
    public void add(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.add(event.getData());
    }

    @Override
    public void remove(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.remove((int)event.getData().get(EEventDataKeys.Id));
    }

    @Override
    public void update(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.update(event.getData());
    }

    @Override
    public void get(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.search(event.getData());
    }

    @Override
    public void subscribe(StoreEvent event) {
        subscribers.get(event.getEvent()).add((IEventSubscriber) event.getData().get(EEventDataKeys.Subscriber));   
    }

    @Override
    public void unsubscribe(StoreEvent event) {
        subscribers.get(event.getEvent()).remove((IEventSubscriber) event.getData().get(EEventDataKeys.Subscriber));   
    }

    @Override
    public void dispatch(StoreEvent event) {
        EStoreEventAction action = event.getAction();
        actions.get(action).accept(event);
    }

    @Override
    public void notifySubscribers(StoreEvent event) {
        for(IEventSubscriber subscriber : subscribers.get(event.getEvent())) {
            subscriber.notifyEvent(event);
        }
    }


    private void setupSubscribers() {
        for(EStoreEvents event : EStoreEvents.values()) {
              subscribers.put(event, new ArrayList<IEventSubscriber>());
        }
    }

    private void setupActions(){
        actions.put(EStoreEventAction.Add, this::add);
        actions.put(EStoreEventAction.Remove, this::remove);
        actions.put(EStoreEventAction.Update, this::update);
        actions.put(EStoreEventAction.Get, this::get);
        actions.put(EStoreEventAction.Subscribe, this::subscribe);
        actions.put(EStoreEventAction.Unsubscribe, this::unsubscribe);
        actions.put(EStoreEventAction.Notify, this::notifySubscribers);
    }

    
}
