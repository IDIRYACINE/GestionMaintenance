package idir.embag.EventStore.Stores.DataStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
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
       
    public DataStore(IDataDelegate[] delegates) {

       dataDelegates.put(EStoreEvents.StockEvent, delegates[IDataStore.STOCK_DELEGATE]);
       dataDelegates.put(EStoreEvents.InventoryEvent, delegates[IDataStore.INVENTORY_DELEGATE]);
       dataDelegates.put(EStoreEvents.FamilyCodeEvent, delegates[IDataStore.FAMILY_DELEGATE]);
       dataDelegates.put(EStoreEvents.SessionRecordsEvent, delegates[IDataStore.HISTORY_DELEGATE]);
       dataDelegates.put(EStoreEvents.WorkersEvent, delegates[IDataStore.WORKER_DELEGATE]);
       dataDelegates.put(EStoreEvents.SessionWorkerEvent, delegates[IDataStore.SESSION_WORKER_DELEGATE]);
       dataDelegates.put(EStoreEvents.SessionGroupEvent, delegates[IDataStore.SESSION_GROUP_DELEGATE]);
       dataDelegates.put(EStoreEvents.SessionEvent, delegates[IDataStore.SESSION_DELEGATE]);
        
       dataDelegates.put(EStoreEvents.UsersEvent, delegates[IDataStore.USER_DELEGATE]);
       dataDelegates.put(EStoreEvents.DesignationEvent, delegates[IDataStore.DESIGNATION_DELEGATE]);
       dataDelegates.put(EStoreEvents.PermissionsEvent, delegates[IDataStore.PERMISSION_DELEGATE]);
       dataDelegates.put(EStoreEvents.GroupPermissionsEvent, delegates[IDataStore.GROUP_PERMISSION_DELEGATE]);


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
        dataDelegate.remove(event.getData());
    }

    @Override
    public void update(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.update(event.getData());
    }

    @Override
    public void search(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.search(event.getData());
    }

    @Override
    public void subscribe(StoreEvent event) {
        subscribers.get(event.getEvent()).add((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));   
    }

    @Override
    public void unsubscribe(StoreEvent event) {
        subscribers.get(event.getEvent()).remove((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));   
    }

    @Override
    public void dispatch(StoreEvent event) {
        EStoreEventAction action = event.getAction();
        actions.get(action).accept(event);
    }


    @Override
    public void load(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.load(event.getData());
    }

    @Override
    public void broadcast(StoreEvent event) {
        for(IEventSubscriber subscriber : subscribers.get(event.getEvent())) {
            subscriber.notifyEvent(event);
        }
    }

    
    @Override
    public void importCollection(StoreEvent event) {
        IDataDelegate dataDelegate = dataDelegates.get(event.getEvent());
        dataDelegate.importCollection(event.getData());
    }

    @Override
    public void notifySubscriber(StoreEvent event) {
        try {
            ((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber)).notifyEvent(event);
        } catch (Exception e) {
            broadcast(event);
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
        actions.put(EStoreEventAction.Search, this::search);
        actions.put(EStoreEventAction.Load, this::load);
        actions.put(EStoreEventAction.Subscribe, this::subscribe);
        actions.put(EStoreEventAction.Unsubscribe, this::unsubscribe);
        actions.put(EStoreEventAction.Notify, this::notifySubscriber);
        actions.put(EStoreEventAction.Broadcast, this::broadcast);
        actions.put(EStoreEventAction.Import, this::importCollection);

        actions.put(EStoreEventAction.CloseSession, this::remove);
    }
    
}
