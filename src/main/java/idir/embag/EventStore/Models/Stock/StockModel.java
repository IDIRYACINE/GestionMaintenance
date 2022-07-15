package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.Database.IProductQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;

@SuppressWarnings("unchecked")
public class StockModel implements IDataDelegate{

    IProductQuery productQuery;

    public StockModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;

        try {
            productQuery.RegisterStockProduct(result.values());
            IProduct product = buildProduct(result);

            Map<EEventDataKeys,Object> otherData = new HashMap<>();
            otherData.put(EEventDataKeys.Product, product);

            dispatchEvent(EStores.DataStore, EStoreEvents.NotificationEvent, EStoreEventAction.Notify, otherData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
        try {
            productQuery.UnregisterStockProduct((int) result.get(EEventDataKeys.Id).getValue());
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(Object data) {
        Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
        try {
            productQuery.UpdateStockProduct((int) result.get(EEventDataKeys.Id).getValue(),result.values());
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;
        /*  TODO : Implement
        try {
            result = productQuery.SearchStockProduct((SearchWrapper) data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }*/
        
        return result;
    }

    private IProduct buildProduct(Map<EEventDataKeys,AttributeWrapper> data) {
        int articleId = (int) data.get(EEventDataKeys.ArticleId).getValue();
        String name = (String) data.get(EEventDataKeys.ArticleName).getValue();
        int quantity = (int) data.get(EEventDataKeys.Quantity).getValue();
        int price = (int) data.get(EEventDataKeys.Price).getValue();
        int familyCode = (int) data.get(EEventDataKeys.FamilyCode).getValue();
        int codebar = (int) data.get(EEventDataKeys.Codebar).getValue();

        IProduct product = new StockProduct(articleId, name, codebar, quantity, price, familyCode);

        return product;
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
}
