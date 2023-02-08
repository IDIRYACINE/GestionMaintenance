package idir.embag.EventStore.Models.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.Database.IProductQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class InventoryModel implements IDataDelegate {

    IProductQuery productQuery;
    InventoryRepository inventoryRepository;

    public InventoryModel(InventoryRepository inventoryRepository) {
        this.productQuery = productQuery;
        this.inventoryRepository = inventoryRepository;
    }

    public void add(Map<EEventsDataKeys,Object> data) {
        try {
            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);

            productQuery.RegisterInventoryProduct(wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventsDataKeys,Object> data) {
        try {
            InventoryProduct product = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);

            productQuery.UnregisterInventoryProduct(product.getArticleId(),product.getAffectationId());
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventsDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);
        InventoryProduct product = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);

        try {
            productQuery.UpdateInventoryProduct(product.getArticleId() ,wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventsDataKeys,Object> data) {
        try {
            SearchWrapper searchParams =DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.SearchWrapper);

            ResultSet result = productQuery.SearchInventoryProduct(searchParams);
            Collection<InventoryProduct> products = inventoryRepository.resultSetToProduct(result);

            data.put(EEventsDataKeys.InstanceCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys,Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.LoadWrapper);
        try{
            ResultSet rawData = productQuery.LoadInventoryProduct(loadWrapper);
            Collection<InventoryProduct> products = inventoryRepository.resultSetToProduct(rawData);
            
            if(products.size() == 0){
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.NoData);
            }else{
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.HasData);
            }   
            
            data.put(EEventsDataKeys.InstanceCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Load, data);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }

    @Override
    public void importCollection(Map<EEventsDataKeys, Object> data) {
        try {
            Collection<AttributeWrapper[]> attributesCollection = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesListCollection);

            productQuery.RegisterInventoryCollection(attributesCollection);
            data.put(EEventsDataKeys.OperationStatus, EOperationStatus.Completed);

            notfiyEvent(EStores.DataConverterStore, EStoreEvents.InventoryEvent, EStoreEventAction.Import, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

}

    

