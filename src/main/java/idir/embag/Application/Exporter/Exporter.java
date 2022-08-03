package idir.embag.Application.Exporter;

import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
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

    private void exportData(){
        
        Map<EEventDataKeys, Object> data = new HashMap<EEventDataKeys, Object>();
        data.put(EEventDataKeys.Subscriber, this);
        data.put(EEventDataKeys.ExportWrapper, exportWrapper);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, exportWrapper.getTargetTable(), EStoreEventAction.Export, data);
        storeCenter.dispatch(event);

        isExporting = true;
    }

    private void importData(){
        Map<EEventDataKeys, Object> data = new HashMap<EEventDataKeys, Object>();
        data.put(EEventDataKeys.Subscriber, this);
        data.put(EEventDataKeys.ImportWrapper, importWrapper);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, importWrapper.getTargetTable(), EStoreEventAction.Import, data);
        storeCenter.dispatch(event);
    }

    public void exportDataFromDatabase(EStoreEvents exportedTable){
        Map<EEventDataKeys, Object> data = new HashMap<EEventDataKeys, Object>();
        data.put(EEventDataKeys.Subscriber, this);
        data.put(EEventDataKeys.ExportWrapper, exportWrapper);

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
        switch(event.getAction()){
            case Export:
                exportData();
                break;
            case Done:
                nextDataSet();
                break;
            case Stop:
                onNoData();
                break;    
            case NoData:
                onNoData();
                break;
            default:
                break;    
        }
    }
    
    private void nextDataSet(){
        if(isExporting){
            exportWrapper.nextRowPatch();
            exportData();
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


   
    
}
