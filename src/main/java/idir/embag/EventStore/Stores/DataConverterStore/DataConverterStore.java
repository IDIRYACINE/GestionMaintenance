package idir.embag.EventStore.Stores.DataConverterStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterStore;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class DataConverterStore implements IDataConverterStore{

    private Map<EStoreEvents,IDataConverterDelegate> delegates = new HashMap<>();
    
    private ArrayList<IEventSubscriber> subscribers = new ArrayList<>();

    private Map<EStoreEventAction , Consumer<StoreEvent>> actions = new HashMap<>();

    public DataConverterStore(IDataConverterDelegate[] delegatesList) {
        delegates.put(EStoreEvents.StockEvent, delegatesList[IDataConverterStore.STOCK_DELEGATE]);
        delegates.put(EStoreEvents.InventoryEvent, delegatesList[IDataConverterStore.INVENTORY_DELEGATE]);
        delegates.put(EStoreEvents.FamilyCodeEvent, delegatesList[IDataConverterStore.FAMILY_DELEGATE]);
        delegates.put(EStoreEvents.SessionRecordsEvent, delegatesList[IDataConverterStore.SESSION_DELEGATE]);
        delegates.put(EStoreEvents.WorkersEvent, delegatesList[IDataConverterStore.WORKER_DELEGATE]);
        delegates.put(EStoreEvents.SessionEvent, delegatesList[IDataConverterStore.SESSION_DELEGATE]);

        setupActions();
    }    

    @Override
    public void dispatch(StoreEvent event) {
        EStoreEventAction action = event.getAction();
        actions.get(action).accept(event);
    }

    @Override
    public void subscribe(StoreEvent event) {
        subscribers.add((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));   
    }

    @Override
    public void unsubscribe(StoreEvent event) {
        subscribers.remove((IEventSubscriber) event.getData().get(EEventsDataKeys.Subscriber));   
    }

    @Override
    public void exportData(StoreEvent event) {
        IDataConverterDelegate dataDelegate = delegates.get(event.getEvent());
        dataDelegate.exportData(event.getData());
    }

    @Override
    public void importData(StoreEvent event) {
        IDataConverterDelegate dataDelegate = delegates.get(event.getEvent());
        dataDelegate.importData(event.getData());
        
    }

    private void setupActions(){
        actions.put(EStoreEventAction.Subscribe, this::subscribe);
        actions.put(EStoreEventAction.Unsubscribe, this::unsubscribe);
        actions.put(EStoreEventAction.Notify, this::notifySubscriber);
        actions.put(EStoreEventAction.Broadcast, this::broadcast);
        actions.put(EStoreEventAction.Export, this::exportData);
        actions.put(EStoreEventAction.Import, this::importData);

    }

    @Override
    public void notifySubscriber(StoreEvent event) {
        for(IEventSubscriber subscriber : subscribers) {
            subscriber.notifyEvent(event);
        }        
    }

    @Override
    public void broadcast(StoreEvent event) {
        for(IEventSubscriber subscriber : subscribers) {
            subscriber.notifyEvent(event);
        }    
        
    }
    
}
