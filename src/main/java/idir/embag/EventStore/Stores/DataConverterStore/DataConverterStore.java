package idir.embag.EventStore.Stores.DataConverterStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Generics.EExporters;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterStore;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class DataConverterStore implements IDataConverterStore{

    private Map<EExporters,IDataConverterDelegate> delegates = new HashMap<>();
    
    private ArrayList<IEventSubscriber> subscribers = new ArrayList<>();

    private Map<EStoreEventAction , Consumer<StoreEvent>> actions = new HashMap<>();

    public DataConverterStore(IDataConverterDelegate[] delegatesList) {
        delegates.put(EExporters.Excel, delegatesList[IDataConverterStore.EXCEL_DELEGATE]);
        delegates.put(EExporters.Report, delegatesList[IDataConverterStore.REPORT_DELEGATE]);
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
        IDataConverterDelegate dataDelegate = delegates.get(EExporters.Excel);

        Map<EEventsDataKeys,Object> data = event.getData();
        data.put(EEventsDataKeys.EventKey, event.getEvent());

        dataDelegate.exportData(data);
    }

    @Override
    public void importData(StoreEvent event) {
        IDataConverterDelegate dataDelegate = delegates.get(EExporters.Excel);

        Map<EEventsDataKeys,Object> data = event.getData();
        data.put(EEventsDataKeys.EventKey, event.getEvent());

        dataDelegate.importData(data);;
        
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
