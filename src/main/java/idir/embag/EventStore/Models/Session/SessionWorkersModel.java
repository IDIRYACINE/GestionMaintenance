package idir.embag.EventStore.Models.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.SessionRepository;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
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
public class SessionWorkersModel implements IDataDelegate {

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;
    
    public SessionWorkersModel(ISessionQuery sessionQuery, SessionRepository sessionRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
    }

    public void add(Map<EEventDataKeys,Object> data) {
        try {
          sessionQuery.RegsiterSessionWorker((Collection<AttributeWrapper>)data.get(EEventDataKeys.AttributeWrappersList));
            ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {

       try {
        sessionQuery.UnregisterGroupWorker((int) data.get(EEventDataKeys.SessionWorkerId));
        ((Runnable) data.get(EEventDataKeys.OnSucessCallback)).run();
    } catch (SQLException e) {
        e.printStackTrace();
    }
        
    }

    @Override
    public void update(Map<EEventDataKeys,Object> data) {
    }

    @Override
    public void search(Map<EEventDataKeys,Object> data) {
        try {
            SearchWrapper searchParams = (SearchWrapper)data.get(EEventDataKeys.SearchWrapper);

            ResultSet result = sessionQuery.SearchSessionWorker(searchParams);
            Collection<SessionWorker> workers = sessionRepository.resultSetToWorker(result);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.SessionWorkersCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Search, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventDataKeys,Object> data) {
        LoadWrapper loadWrapper = new LoadWrapper(10,0);
        try{
            ResultSet rawData = sessionQuery.LoadSessionWorkers(loadWrapper);
            Collection<SessionWorker> workers = sessionRepository.resultSetToWorker(rawData);

            Map<EEventDataKeys,Object> response = new HashMap<>();
            response.put(EEventDataKeys.SessionWorkersCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Load, response);
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
