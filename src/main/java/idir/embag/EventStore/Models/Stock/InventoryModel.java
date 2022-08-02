package idir.embag.EventStore.Models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

@SuppressWarnings("unchecked")
public class InventoryModel implements IDataDelegate {

    IProductQuery productQuery;
    InventoryRepository inventoryRepository;

    public InventoryModel(IProductQuery productQuery,InventoryRepository inventoryRepository) {
        this.productQuery = productQuery;
        this.inventoryRepository = inventoryRepository;
    }

    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterInventoryProduct((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.UnregisterInventoryProduct((int) data.get(EEventDataKeys.ArticleId));
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);

        try {
            productQuery.UpdateInventoryProduct((int) data.get(EEventDataKeys.ArticleId) ,wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventDataKeys,Object> data) {
        try {
            SearchWrapper searchParams = (SearchWrapper)data.get(EEventDataKeys.SearchWrapper);

            ResultSet result = productQuery.SearchInventoryProduct(searchParams);
            Collection<IProduct> products = inventoryRepository.resultSetToProduct(result);

            data.put(EEventDataKeys.ProductsCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        LoadWrapper loadWrapper = (LoadWrapper)data.get(EEventDataKeys.LoadWrapper);
        try{
            ResultSet rawData = productQuery.LoadInventoryProduct(loadWrapper);
            Collection<IProduct> products = inventoryRepository.resultSetToProduct(rawData);

            data.put(EEventDataKeys.ProductsCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Load, data);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }

}

    

