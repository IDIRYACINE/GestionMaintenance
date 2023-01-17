package idir.embag.EventStore.Models.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.DataConverters.Excel.CellWriters.ReportExcelCellWriter;
import idir.embag.Infrastructure.DataConverters.Report.Excel;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.IDataConverter;
import idir.embag.Types.Infrastructure.DataConverters.ReportWrapper;
import idir.embag.Types.Infrastructure.DataConverters.Excel.IExcelCellWriter;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Metadata.EScannedBarcodeAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReportModel implements IDataConverterDelegate {
    IProductQuery query;
    InventoryRepository inventoryRepository;
    private IExcelCellWriter writeDelegate;
    private IDataConverter excelConverter;

    public ReportModel(IProductQuery query, InventoryRepository inventoryRepository) {
        this.query = query;
        this.inventoryRepository = inventoryRepository;
        excelConverter = new Excel();
        writeDelegate = new ReportExcelCellWriter();
    }

    @Override
    public void exportData(Map<EEventsDataKeys, Object> data) {

        try {
            ResultSet resultSet;
            resultSet = query.LoadScannedInventory();

            String positiveBarcodes = getInventoryBarcodesList(resultSet);

            resultSet = query.LoadProductsByInBarcodes(positiveBarcodes, false);
            Collection<InventoryProduct> scannedInventoryItems = inventoryRepository.resultSetToProduct(resultSet);

            resultSet = query.LoadProductsByInBarcodes(positiveBarcodes, true);
            Collection<InventoryProduct> negativeInventoryItems = inventoryRepository.resultSetToProduct(resultSet);

            ExportWrapper exportWrapper = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.ExportWrapper);

            ReportWrapper reportWrapper = new ReportWrapper(scannedInventoryItems, negativeInventoryItems);
            ArrayList<ReportWrapper> reportWrappers = new ArrayList<>();
            reportWrappers.add(reportWrapper);

            excelConverter.setupExport(exportWrapper);
            excelConverter.exportData(writeDelegate,
                    reportWrappers);

            data.put(EEventsDataKeys.OperationStatus, EOperationStatus.Completed);

            StoreCenter storeCenter = StoreCenter.getInstance();
            StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.ReportEvent,
                    EStoreEventAction.Export, data);
            storeCenter.notify(action);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importData(Map<EEventsDataKeys, Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setup(Map<EEventsDataKeys, Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getInventoryBarcodesList(ResultSet source) {
        String barcodes = "";

        try {
            while (source.next()) {
                String barcode = source.getString(EScannedBarcodeAttributes.ScannedCodebar.toString());
                barcodes += barcode;
                if (!source.last())
                    barcodes += ",";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return barcodes;
    }

}
