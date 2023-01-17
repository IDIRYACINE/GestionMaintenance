package idir.embag.Ui.Dialogs.ReportDialog;

import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Generics.EExporters;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class ReportController implements IEventSubscriber  {
    private Runnable doneCallback;


    public void startExport(ExportWrapper exportWrapper) {
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        data.put(EEventsDataKeys.ExporterKey, EExporters.Report);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, EStoreEvents.ReportEvent,
                EStoreEventAction.Export, data);
        storeCenter.dispatch(event);
    }

    public void cancel() {
        doneCallback.run();
    }

    public void setDoneCallback(Runnable callback) {
        doneCallback = callback;
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        doneCallback.run();
    }
    
}
