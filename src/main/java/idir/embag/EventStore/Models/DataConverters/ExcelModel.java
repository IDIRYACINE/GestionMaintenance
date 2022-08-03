package idir.embag.EventStore.Models.DataConverters;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Infrastructure.DataConverters.Excel.Excel;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class ExcelModel implements IDataConverterDelegate{

    private IDataConverter excelConverter;

    public ExcelModel() {
        Excel excel = new Excel();
        this.excelConverter = excel;
    }

    @Override
    public void exportData(Map<EEventDataKeys, Object> data) {

        excelConverter.exportData();
    }

    @Override
    public void importData(Map<EEventDataKeys, Object> data) {
        excelConverter.importData();
    }

    /* (non-Javadoc)
     * @see idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate#setup(java.util.Map)
     */
    @Override
    public void setup(Map<EEventDataKeys, Object> data) {
       
        switch((EStoreEvents)data.get(EEventDataKeys.EStoreEvent)){
            case StockEvent: setStockHelper();
                break;
            case InventoryEvent : setInventoryHelper();
                break;    
            case SessionRecordsEvent : setSessionRecordsHelper();
                break;
            case SessionEvent : setSessionHelper();
                break;
            case WorkersEvent : setWorkersHelper();
                break;    
            default:
                break;
        }
        
    }

    private void setSessionHelper() {
        
    }

    private void setSessionRecordsHelper() {
    }

    private void setWorkersHelper() {
    }

    private void setStockHelper(){

    }

    private void setInventoryHelper(){

    }



    
}


    

