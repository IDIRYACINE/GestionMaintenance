package idir.embag.EventStore.Models.Workers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.WorkersRepository;
import idir.embag.Types.Infrastructure.Database.IWorkerQuery;
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
public class WorkersModel implements IDataDelegate{

    IWorkerQuery workerQuery;
    WorkersRepository workerRepository;

    public WorkersModel(IWorkerQuery workerQuery,WorkersRepository workerRepository) {
        this.workerQuery = workerQuery;
        this.workerRepository = workerRepository;
    }

    public void add(Map<EEventDataKeys,Object> data) {
        try {
            workerQuery.RegisterWorker((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventDataKeys,Object> data) {
        try {
            workerQuery.UnregisterWorker((int) data.get(EEventDataKeys.WorkerId));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Map<EEventDataKeys,Object> data) {
        Collection<AttributeWrapper> wrappers = (Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList);

        try {
            workerQuery.UpdateWorker((int) data.get(EEventDataKeys.WorkerId) ,wrappers);
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventDataKeys,Object> data) {
        try {
            SearchWrapper searchParams = (SearchWrapper)data.get(EEventDataKeys.SearchWrapper);

            ResultSet result = workerQuery.SearchWorker(searchParams);
            Collection<Worker> products = workerRepository.resultSetToProduct(result);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.WorkersCollection, products);
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Search, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        LoadWrapper loadWrapper = new LoadWrapper(10,0);
        try{
            ResultSet rawData = workerQuery.LoadSWorkers(loadWrapper);
            Collection<Worker> workers = workerRepository.resultSetToProduct(rawData);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.WorkersCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Load, response);
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
