package idir.embag.EventStore.Models.DataConverters;

import java.util.Collection;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Infrastructure.DataConverters.Excel.Excel;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.FamilyCodeCellWriter;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;

@SuppressWarnings({"unchecked","rawtypes"})
public class ExcelModel implements IDataConverterDelegate{

    private IDataConverter excelConverter;
    //private IExcelCellReader excelCellReader;
    private IExcelCellWriter excelCellWriter;

    public ExcelModel() {
        Excel excel = new Excel();
        this.excelConverter = excel;

        excelCellWriter = new FamilyCodeCellWriter();
    }

    @Override
    public void exportData(Map<EEventDataKeys, Object> data) {
        excelConverter.setupExport((ExportWrapper) data.get(EEventDataKeys.ExportWrapper));
        excelConverter.exportData(excelCellWriter, (Collection<IProduct>) data.get(EEventDataKeys.ProductsCollection));
     }

    @Override
    public void importData(Map<EEventDataKeys, Object> data) {
        excelConverter.importData();
    }

    
  

    // private void exportSession(Collection<Session> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void exportSessionRecords(Collection<SessionRecord> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void exportWorkers(Collection<Worker> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void exportStock(Collection<IProduct> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void exportInventory(Collection<IProduct> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void exportFamilyCode(Collection<IProduct> data) {
    //     excelConverter.exportData(excelCellWriter, data);
    // }

    // private void setupFamilyCode(ExportWrapper wrapper){
    //     excelConverter.setupExport(wrapper);
    // }
}


    

