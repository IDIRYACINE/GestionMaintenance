package idir.embag.Infrastructure.Server.WebSocket.ResponseHandlers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.DataModels.SocketApisData.DProductDetaills;
import idir.embag.DataModels.SocketApisData.DSubmitRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;

public class SessionRecordHandler {

    private InventoryRepository inventoryRepository = new InventoryRepository();

    public DProductDetaills handleRecord(DSubmitRecord requestData) {

        InventoryProduct product = getInventoryProduct(requestData.barcode);
        DProductDetaills productDetails =
        DProductDetaills.fromInventoryProduct(product, requestData.requestTimestamp);

        SessionRecord sessionRecord = new SessionRecord(
        product.getArticleId(),
        product.getArticleName(),
        requestData.scannedDate.toString(),
        String.valueOf(product.getPrice()),
        "0.0",
        "0.0",
        "0.0",
        "0.0",
        requestData.groupId,
        requestData.workerName);

        // for testing
        // SessionRecord sessionRecord = new SessionRecord(
        //         33,
        //         "name",
        //         requestData.scannedDate.toString(),
        //         "450",
        //         "0.0",
        //         "0.0",
        //         "0.0",
        //         "0.0",
        //         "45",
        //         "idir");

        SessionRecord[] values = { sessionRecord };
        EEventsDataKeys[] keys = { EEventsDataKeys.Instance };
        Map<EEventsDataKeys, Object> data = DataBundler.bundleData(keys, values);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore,
                EStoreEvents.SessionRecordsEvent, EStoreEventAction.Add, data);

        storeCenter.notify(action);
        
        // for testing
        // new DProductDetaills(0,"name","location",22,requestData.scannedDate);

        return productDetails;
    }

    @SuppressWarnings({ "unchecked" })
    public void handleRecordCollection(Collection<SessionRecord> recordCollection) {
        Collection<SessionRecord>[] values = new Collection[1];
        values[0] = recordCollection;

        EEventsDataKeys[] keys = { EEventsDataKeys.InstanceCollection };
        Map<EEventsDataKeys, Object> data = DataBundler.bundleData(keys, values);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch action = storeCenter.createStoreEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent,
                EStoreEventAction.AddCollection, data);
        storeCenter.broadcast(action);
    }

    private InventoryProduct getInventoryProduct(int barcode) {
        IProductQuery query = ServicesProvider.getInstance().getDatabaseInitialiser().getProductQuery();

        try {
            AttributeWrapper barcodeAttr = new AttributeWrapper(EInventoryAttributes.ArticleCode, barcode);
            ArrayList<AttributeWrapper> attrs = new ArrayList<>();
            attrs.add(barcodeAttr);

            SearchWrapper params = new SearchWrapper(attrs);
            ResultSet result = query.SearchInventoryProduct(params);

            Collection<InventoryProduct> products = inventoryRepository.resultSetToProduct(result);

            InventoryProduct product = products.iterator().next();
            return product;
        } catch (Exception e) {
            return null;
        }
    }
}
