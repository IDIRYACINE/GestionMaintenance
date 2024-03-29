package idir.embag.EventStore.Models.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.RegisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.UnregisterSessionWorkerWrapper;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.UpdateSessionWorkerWrapper;
import idir.embag.Repository.SessionRepository;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.Infrastructure.Server.IServer;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

@SuppressWarnings("unused")
public class SessionWorkersModel implements IDataDelegate {

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;
    
    public SessionWorkersModel(ISessionQuery sessionQuery, SessionRepository sessionRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
    }

    public void add(Map<EEventsDataKeys,Object> data) {
        try {
            // SessionWorker worker = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);
            // registerSessionWorkerOnServer(worker);

            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);
            sessionQuery.RegsiterSessionWorker(wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Map<EEventsDataKeys,Object> data) {

       try {
        SessionWorker worker = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);
        // unregisterSessionWorkerOnServer(worker);

        sessionQuery.UnregisterGroupWorker(worker.getWorkerId());
        notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
        e.printStackTrace();
        }
        
    }

    @Override
    public void update(Map<EEventsDataKeys,Object> data) {
        try {
            SessionWorker worker = DataBundler.retrieveValue(data,EEventsDataKeys.Instance);
            updateSessionWorkerOnServer(worker);

            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);
            sessionQuery.UpdateSessionWorker(worker.getWorkerId(),wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Update, data);
            } catch (SQLException e) {
            e.printStackTrace();
            }
    }

    @Override
    public void search(Map<EEventsDataKeys,Object> data) {
        try {
            SearchWrapper searchParams =DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.SearchWrapper);

            ResultSet result = sessionQuery.SearchSessionWorker(searchParams);
            Collection<SessionWorker> workers = sessionRepository.resultSetToWorker(result);

            data.put(EEventsDataKeys.InstanceCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys,Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.LoadWrapper);
        try{
            ResultSet rawData = sessionQuery.LoadSessionWorkers(loadWrapper);
            Collection<SessionWorker> workers = sessionRepository.resultSetToWorker(rawData);

            data.put(EEventsDataKeys.InstanceCollection, workers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Load, data);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void importCollection(Map<EEventsDataKeys, Object> data) {
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }

    private void registerSessionWorkerOnServer(SessionWorker worker){
        RegisterSessionWorkerWrapper wrapper = new RegisterSessionWorkerWrapper(worker);

        Map<EServerKeys,Object> data =  new HashMap<>();
        DataBundler.appendData(data, EServerKeys.ApiWrapper, wrapper);

        IServer server = ServicesProvider.getInstance().getRemoteServer();
        server.dispatchApiCall(data);
    }
    
    private void unregisterSessionWorkerOnServer(SessionWorker worker){
        UnregisterSessionWorkerWrapper wrapper = new UnregisterSessionWorkerWrapper(worker);

        Map<EServerKeys,Object> data =  new HashMap<>();
        DataBundler.appendData(data, EServerKeys.ApiWrapper, wrapper);

        IServer server = ServicesProvider.getInstance().getRemoteServer();
        server.dispatchApiCall(data);
    }

    private void updateSessionWorkerOnServer(SessionWorker worker){
        UpdateSessionWorkerWrapper wrapper = new UpdateSessionWorkerWrapper(worker);

        Map<EServerKeys,Object> data =  new HashMap<>();
        DataBundler.appendData(data, EServerKeys.ApiWrapper, wrapper);

        IServer server = ServicesProvider.getInstance().getRemoteServer();
        server.dispatchApiCall(data);
    }

}    
