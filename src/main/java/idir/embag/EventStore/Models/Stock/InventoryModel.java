package idir.embag.EventStore.Models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
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
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.UnregisterInventoryProduct((int) data.get(EEventDataKeys.ArticleId));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);

        try {
            productQuery.UpdateInventoryProduct((int) data.get(EEventDataKeys.ArticleId) ,wrappers);
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventDataKeys,Object> data) {

       
       /* Todo : implement this
       try {
        result = productQuery.SearchInventoryProduct((SearchWrapper) data);
        } catch (SQLException e) {
        e.printStackTrace();
        }*/

    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        //TODO : change method to get a proper load wrapper
        LoadWrapper loadWrapper = new LoadWrapper(10,0);
        try{
            ResultSet rawData = productQuery.LoadInventoryProduct(loadWrapper);
            Collection<IProduct> products = inventoryRepository.resultSetToProduct(rawData);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.ProductsCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Load, response);
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

    

