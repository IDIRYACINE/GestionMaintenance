package idir.embag.DataModels.SocketApisData;

import java.sql.Timestamp;

import idir.embag.DataModels.Products.InventoryProduct;

public class DProductDetaills {
    public final String event = "onProductDetails";
    public int barcode;
    public String itemName;
    public String locationName;
    public int locationId;
    public Timestamp requestTimestamp;

    public DProductDetaills(int barcode, String itemName, String locationName, int locationId,
            Timestamp requestTimestamp) {
        this.barcode = barcode;
        this.itemName = itemName;
        this.locationName = locationName;
        this.locationId = locationId;
        this.requestTimestamp = requestTimestamp;
    }

    public DProductDetaills() {
    }

    public static DProductDetaills fromInventoryProduct(InventoryProduct product, Timestamp requestTimestamp){

        DProductDetaills details = new DProductDetaills();
        details.barcode = product.getArticleCode();
        details.itemName = product.getArticleName();
        details.locationName = "Location Name";
        details.locationId = product.getAffectationId();
        details.requestTimestamp = requestTimestamp;

        return details;
    }
}
