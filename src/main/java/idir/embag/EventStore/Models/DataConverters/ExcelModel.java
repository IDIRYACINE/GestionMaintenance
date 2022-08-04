package idir.embag.EventStore.Models.DataConverters;

import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.DataConverters.Excel.Excel;
import idir.embag.Infrastructure.DataConverters.Excel.CellReaders.FamilyCodeCellReader;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.FamilyCodeCellWriter;
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
    private IExcelCellReader excelCellReader;
    private IExcelCellWriter excelCellWriter;

    public ExcelModel() {
        Excel excel = new Excel();
        this.excelConverter = excel;

        excelCellWriter = new FamilyCodeCellWriter();
        excelCellReader = new FamilyCodeCellReader();
    }

    @Override
    public void exportData(Map<EEventsDataKeys, Object> data) {
        
        excelConverter.setupExport(DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,EWrappers.ExportWrapper));
        excelConverter.exportData(excelCellWriter, (Collection<IProduct>) data.get(EEventsDataKeys.InstanceCollection));
     }

    @Override
    public void importData(Map<EEventsDataKeys, Object> data) {

        excelConverter.setupImport(DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,EWrappers.ImportWrapper));
        Collection<AttributeWrapper[]> loadedData = excelConverter.importData(excelCellReader);

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesListCollection, loadedData);
        DataBundler.appendData(data, EEventsDataKeys.Subscriber, this);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Import, data);
        storeCenter.dispatch(action);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        Map<EEventsDataKeys, Object> data = event.getData();

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataConverterStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Import, data);
        storeCenter.broadcast(action);
        
    }

  
}


    

