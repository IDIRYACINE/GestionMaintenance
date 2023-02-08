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
import idir.embag.Types.Generics.EOperationStatus;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
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

public class HistoryModel implements IDataDelegate{

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;
    
    public HistoryModel(SessionRepository sessionRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void add(Map<EEventsDataKeys,Object> data) {
        try {
          sessionQuery.RegisterSessionRecord(DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection));
          notfiyEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Map<EEventsDataKeys,Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Map<EEventsDataKeys,Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
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
          
            if(records.size() == 0){
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.NoData);
            }else{
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.HasData);
            }   

            data.put(EEventsDataKeys.InstanceCollection, records);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Load, data);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void importCollection(Map<EEventsDataKeys, Object> data) {
        try {
            Collection<AttributeWrapper[]> attributesCollection = DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesListCollection);

            sessionQuery.RegisterSessionRecordCollection(attributesCollection);
            data.put(EEventsDataKeys.OperationStatus, EOperationStatus.Completed);

            notfiyEvent(EStores.DataConverterStore, EStoreEvents.InventoryEvent, EStoreEventAction.Import, data);
        } catch (SQLException e) {
           
            e.printStackTrace();
        }
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }
    
}
