package idir.embag.Types.Infrastructure.DataConverters;

import java.util.Collection;

import idir.embag.DataModels.Products.InventoryProduct;

public class ReportWrapper {
    private Collection<InventoryProduct> scannedInventoryItems;
    private Collection<InventoryProduct> negativeInventoryItems;

    public ReportWrapper(Collection<InventoryProduct> scannedInventoryItems,
            Collection<InventoryProduct> negativeInventoryItems) {
        this.scannedInventoryItems = scannedInventoryItems;
        this.negativeInventoryItems = negativeInventoryItems;
    }

    public Collection<InventoryProduct> getScannedInventoryItems() {
        return scannedInventoryItems;
    }

    public Collection<InventoryProduct> getNegativeInventoryItems() {
        return negativeInventoryItems;
    }

}
