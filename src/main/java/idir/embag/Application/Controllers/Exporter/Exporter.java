package idir.embag.Application.Controllers.Exporter;

import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class Exporter implements IEventSubscriber{

    private boolean isExporting = false;

    private boolean isImporting = false;

    private ExportWrapper exportWrapper;

    private ImportWrapper importWrapper;


    public Exporter() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataConverterStore, null, this);
    }

    private void exportData(Map<EEventsDataKeys, Object> data ){
        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, exportWrapper.getTargetTable(), EStoreEventAction.Export, data);
        storeCenter.dispatch(event);

        isExporting = true;
    }

    private void importData(){
        Map<EEventsDataKeys, Object> data = new HashMap<EEventsDataKeys, Object>();
        data.put(EEventsDataKeys.Subscriber, this);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);
        wrappersData.put(EWrappers.ImportWrapper, importWrapper);

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, importWrapper.getTargetTable(), EStoreEventAction.Import, data);
        storeCenter.dispatch(event);
    }

    private void exportDataFromDatabase(EStoreEvents exportedTable){
        Map<EEventsDataKeys, Object> data = new HashMap<EEventsDataKeys, Object>();
        data.put(EEventsDataKeys.Subscriber, this);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);
        wrappersData.put(EWrappers.LoadWrapper, exportWrapper.getLoadWrapper());

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataStore, exportedTable, EStoreEventAction.Load, data);
        storeCenter.dispatch(event);

    }

    public void startExport(EStoreEvents exportedTable,ExportWrapper exportWrapper){
        this.exportWrapper = exportWrapper;
        exportDataFromDatabase(exportedTable);
    }

    public void startImport(EStoreEvents importedTable,ImportWrapper importWrapper){
        this.importWrapper = importWrapper;
        importData();
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        EOperationStatus status = (EOperationStatus)event.getData().get(EEventsDataKeys.OperationStatus);
        switch(status){
            case HasData:
                exportData(event.getData());
                break;
            case Completed:
                nextDataSet();
                break;
            case Stop:
                onNoData();
                break;    
            case NoData:
                onNoData();
                break;
            default : exportData(event.getData());
                break;    
        }
    }
    
    private void nextDataSet(){
        if(isExporting){
            exportWrapper.nextRowPatch();
            startExport(exportWrapper.getTargetTable(), exportWrapper);
        }
        else if(isImporting){
            importWrapper.nextRowPatch();
            importData();
        }
    }

    private void saveExportProgress(ExportWrapper exportWrapper){

    }

    private void saveImportProgress(ImportWrapper importWrapper){

    }

    private void onNoData(){
        if(isExporting){
            saveExportProgress(exportWrapper);
            exportWrapper = null;
            isExporting = false;
        }

        if(isImporting){
            saveImportProgress(importWrapper);
            importWrapper = null;
            isImporting = false;
        }
    }


   public void unsubscribe(){
        StoreCenter.getInstance().unsubscribeFromEvents(EStores.DataConverterStore,null,this);
    }

    public void cancelExoprt() {
        
    }
    
}
