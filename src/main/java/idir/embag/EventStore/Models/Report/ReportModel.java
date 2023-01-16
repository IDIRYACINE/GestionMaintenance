package idir.embag.EventStore.Models.Report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;

public class ReportModel implements IDataConverterDelegate {
    IProductQuery query;
    InventoryRepository inventoryRepository;

    @Override
    public void exportData(Map<EEventsDataKeys, Object> data) {
        ArrayList<SessionRecord> scannedInventoryItems = DataBundler.retrieveValue(data,
                EEventsDataKeys.InstanceCollection);
        
        String positiveBarcodes = getInventoryBarcodesList(scannedInventoryItems);      
        

        try {
            ResultSet resultSet = query.LoadNegativeProducts(positiveBarcodes);
            Collection<InventoryProduct> negativeInventoryItems = inventoryRepository.resultSetToProduct(resultSet);



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

    private String getInventoryBarcodesList(ArrayList<SessionRecord> scannedInventoryItems) {
        String barcodes = "";
        for (int i = 0 ; i< scannedInventoryItems.size(); i++) {
            barcodes += scannedInventoryItems.get(i).getArticleId();
            if (i < scannedInventoryItems.size() - 1) {
                barcodes += ",";
            }
        }
        return barcodes;
    }

}
