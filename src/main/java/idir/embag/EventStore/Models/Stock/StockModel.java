package idir.embag.EventStore.Models.Stock;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

@SuppressWarnings("unchecked")
public class StockModel implements IDataDelegate{

    IProductQuery productQuery;

    public StockModel(IProductQuery productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        
        try {
            productQuery.RegisterStockProduct((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            IProduct product = buildProduct(data);
            data.put(EEventDataKeys.ProductInstance, product);

            dispatchEvent(EStores.DataStore, EStoreEvents.NotificationEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.ProductInstance);
        
        try {
            productQuery.UnregisterStockProduct(product.getArticleId());
            dispatchEvent(EStores.DataStore, EStoreEvents.NotificationEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.ProductInstance);
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);
        try {
            productQuery.UpdateStockProduct(product.getArticleId(),wrappers);
            updateProduct(product,data);
            dispatchEvent(EStores.DataStore, EStoreEvents.NotificationEvent, EStoreEventAction.Update, data);

        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }

    private void updateProduct(IProduct product, Map<EEventDataKeys, Object> data) {

    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        
        return result;
    }

    private IProduct buildProduct(Map<EEventDataKeys,Object> data) {
        int articleId = (int) data.get(EEventDataKeys.ArticleId);
        String name = (String) data.get(EEventDataKeys.ArticleName);
        int quantity = (int) data.get(EEventDataKeys.Quantity);
        int price = (int) data.get(EEventDataKeys.Price);
        int familyCode = (int) data.get(EEventDataKeys.FamilyCode);
        int codebar = (int) data.get(EEventDataKeys.ArticleCode);

        IProduct product = new StockProduct(articleId, name, codebar, quantity, price, familyCode);

        return product;
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
}
