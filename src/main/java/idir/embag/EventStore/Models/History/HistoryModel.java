package idir.embag.EventStore.Models.History;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.SessionRepository;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class HistoryModel implements IDataDelegate{

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;
    
    public HistoryModel(ISessionQuery sessionQuery,SessionRepository sessionRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
    }

    public void add(Map<EEventsDataKeys,Object> data) {
        try {
          sessionQuery.RegisterSessionRecord(DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection));
          notfiyEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(Map<EEventsDataKeys,Object> data) {
    }

    public void update(Map<EEventsDataKeys,Object> data) {
    }

    @Override
    public void search(Map<EEventsDataKeys,Object> data) {
        try {
            SearchWrapper searchParams =DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.SearchWrapper);

            ResultSet result = sessionQuery.SearchSessionRecord(searchParams);
            Collection<SessionRecord> records = sessionRepository.resultSetToRecord(result);

            data.put(EEventsDataKeys.InstanceCollection, records);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys,Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.LoadWrapper);
        try{
            ResultSet rawData = sessionQuery.LoadSessionRecord(loadWrapper);
            Collection<SessionRecord> records = sessionRepository.resultSetToRecord(rawData);

            data.put(EEventsDataKeys.Instance, records);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Load, data);
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
    
}
