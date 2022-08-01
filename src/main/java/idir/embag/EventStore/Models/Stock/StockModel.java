package idir.embag.EventStore.Models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.StockRepository;
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
public class StockModel implements IDataDelegate{

    IProductQuery productQuery;
    StockRepository stockRepository;

    public StockModel(IProductQuery productQuery,StockRepository stockRepository) {
        this.productQuery = productQuery;
        this.stockRepository = stockRepository;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            productQuery.RegisterStockProduct((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {
        IProduct product = (IProduct)data.get(EEventDataKeys.ProductInstance);
        
        try {
            productQuery.UnregisterStockProduct(product.getArticleId());
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);
        try {
            productQuery.UpdateStockProduct((int) data.get(EEventDataKeys.ArticleId),wrappers);
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        
    }


    @Override
    public void search(Map<EEventDataKeys,Object> data) {
        try {
            SearchWrapper searchParams = (SearchWrapper)data.get(EEventDataKeys.SearchWrapper);

            ResultSet result = productQuery.SearchStockProduct(searchParams);
            Collection<IProduct> products = stockRepository.resultSetToProduct(result);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.ProductsCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Search, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        //TODO : change method to get a proper load wrapper
        LoadWrapper loadWrapper = new LoadWrapper(100,0);
        try{
            ResultSet rawData = productQuery.LoadStockProduct(loadWrapper);
            Collection<IProduct> products = stockRepository.resultSetToProduct(rawData);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.ProductsCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Load, response);
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
