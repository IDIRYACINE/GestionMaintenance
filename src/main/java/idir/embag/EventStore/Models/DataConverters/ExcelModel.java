package idir.embag.EventStore.Models.DataConverters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.DataConverters.Excel.Excel;
import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.FamilyCodeCellReader;
import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.InventoryCellReaderV2;
import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.StockCellReader;
import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.WorkerCellReader;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.FamilyCodeCellWriter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.InventoryCellWriter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.SessionCellWriter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.SessionRecordsWriter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.StockCellWriter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.WorkerCellWriter;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellReader;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

@SuppressWarnings({"unchecked","rawtypes"})
public class ExcelModel implements IDataConverterDelegate , IEventSubscriber{

    private IDataConverter excelConverter;

    private Map<EStoreEvents,IExcelCellReader> readDelegates;
    private Map<EStoreEvents,IExcelCellWriter> writeDelegates;

    public ExcelModel() {
        this.excelConverter = new Excel();
        readDelegates = new HashMap<>();
        writeDelegates = new HashMap<>();
        setupReadDelegates();
        setupWriteDelegates();
    }

    @Override
    public void exportData(Map<EEventsDataKeys, Object> data) {
        EStoreEvents eventKey = DataBundler.retrieveValue(data,EEventsDataKeys.EventKey);
        IExcelCellWriter cellWriter = writeDelegates.get(eventKey);
        ExportWrapper exportWrapper = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,EWrappers.ExportWrapper);
        
        excelConverter.setupExport(exportWrapper);
        excelConverter.exportData(cellWriter, DataBundler.retrieveValue(data,EEventsDataKeys.InstanceCollection));

        data.put(EEventsDataKeys.OperationStatus, EOperationStatus.Completed);
        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, eventKey, EStoreEventAction.Export, data);
        storeCenter.notify(action);
    }

    @Override
    public void importData(Map<EEventsDataKeys, Object> data) {
        EStoreEvents eventKey = DataBundler.retrieveValue(data,EEventsDataKeys.EventKey);

        IExcelCellReader cellReader = readDelegates.get(eventKey);

        excelConverter.setupImport(DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,EWrappers.ImportWrapper));
        Collection<AttributeWrapper[]> loadedData = excelConverter.importData(cellReader);

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesListCollection, loadedData);
        DataBundler.appendData(data, EEventsDataKeys.Subscriber, this);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, eventKey, EStoreEventAction.Import, data);
        storeCenter.dispatch(action);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        Map<EEventsDataKeys, Object> data = event.getData();

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataConverterStore, event.getEvent(), EStoreEventAction.Export, data);
        storeCenter.broadcast(action);
        
    }

    @Override
    public void setup(Map<EEventsDataKeys, Object> data){
    }

    private void setupReadDelegates(){
        readDelegates.put(EStoreEvents.FamilyCodeEvent, new FamilyCodeCellReader());
        readDelegates.put(EStoreEvents.StockEvent, new StockCellReader());
        readDelegates.put(EStoreEvents.InventoryEvent, new InventoryCellReaderV2());
        readDelegates.put(EStoreEvents.WorkersEvent, new WorkerCellReader());
       
    }

    private void setupWriteDelegates(){
        writeDelegates.put(EStoreEvents.FamilyCodeEvent, new FamilyCodeCellWriter());
        writeDelegates.put(EStoreEvents.StockEvent,new StockCellWriter());
        writeDelegates.put(EStoreEvents.InventoryEvent, new InventoryCellWriter());
        writeDelegates.put(EStoreEvents.WorkersEvent, new WorkerCellWriter());
        writeDelegates.put(EStoreEvents.SessionEvent, new SessionCellWriter());
        writeDelegates.put(EStoreEvents.SessionRecordsEvent, new SessionRecordsWriter());
    }
  
}


    

