package idir.embag.EventStore.Models.Workers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.WorkersRepository;
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;
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

public class WorkersModel implements IDataDelegate{

    IWorkerQuery workerQuery;
    WorkersRepository workerRepository;

    public WorkersModel(WorkersRepository workerRepository) {
        this.workerQuery = workerQuery;
        this.workerRepository = workerRepository;
    }

    public void add(Map<EEventsDataKeys,Object> data) {
        try {
            workerQuery.RegisterWorker(DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection));
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Add, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventsDataKeys,Object> data) {
        try {
            Worker worker = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);

            workerQuery.UnregisterWorker(worker.getId());
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventsDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);

        try {
            Worker worker = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);

            workerQuery.UpdateWorker(worker.getId() ,wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventsDataKeys,Object> data) {
        try {
            SearchWrapper searchParams = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys, EWrappers.SearchWrapper);

            ResultSet result = workerQuery.SearchWorker(searchParams);
            Collection<Worker> products = workerRepository.resultSetToProduct(result);

            data.put(EEventsDataKeys.InstanceCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys,Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.LoadWrapper);
        try{
            ResultSet rawData = workerQuery.LoadSWorkers(loadWrapper);
            Collection<Worker> workers = workerRepository.resultSetToProduct(rawData);

            data.put(EEventsDataKeys.InstanceCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Load, data);
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

            workerQuery.RegisterWorkerCollection(attributesCollection);
            data.put(EEventsDataKeys.OperationStatus, EOperationStatus.Completed);
            notfiyEvent(EStores.DataConverterStore, EStoreEvents.WorkersEvent, EStoreEventAction.Import, data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
        
    }
    
}
