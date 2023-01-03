package idir.embag.EventStore.Models.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Users.Designation;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.DesignationsRepository;
import idir.embag.Repository.SessionRepository;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
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

public class SessionGroupModel implements IDataDelegate {

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;
    private IGroupPermissionsQuery groupPermissionsQuery;

    private DesignationsRepository designationsRepository;

    public SessionGroupModel(ISessionQuery sessionQuery, SessionRepository sessionRepository,
            IGroupPermissionsQuery groupPermissionsQuery, DesignationsRepository designationsRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
        this.groupPermissionsQuery = groupPermissionsQuery;
        this.designationsRepository = designationsRepository;
    }

    public void add(Map<EEventsDataKeys, Object> data) {
        try {
            sessionQuery.RegisterSessionGroup(DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.AttributesCollection));
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Add, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Map<EEventsDataKeys, Object> data) {

        try {
            SessionGroup group = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);
            sessionQuery.UnregisterSessionGroup(group.getId());
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {
        Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                EWrappers.AttributesCollection);
        SessionGroup group = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

        try {
            sessionQuery.UpdateSessionGroup(group.getId(), wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {
            SearchWrapper searchParams = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);

            ResultSet result = sessionQuery.SearchSessionGroup(searchParams);
            Collection<SessionGroup> groups = sessionRepository.resultSetToGroup(result);

            data.put(EEventsDataKeys.InstanceCollection, groups);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                EWrappers.LoadWrapper);
        try {
            ResultSet rawData = sessionQuery.LoadSessionGroup(loadWrapper);
            Collection<SessionGroup> groups = sessionRepository.resultSetToGroup(rawData);

            for (SessionGroup group : groups) {
                rawData = groupPermissionsQuery.LoadGroupPermissions(group.getId());
                ArrayList<Designation> designations = designationsRepository.resultSetToDesignation(rawData);

                group.setDesignations(designations);
            }

            data.put(EEventsDataKeys.InstanceCollection, groups);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Load, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }

    @Override
    public void importCollection(Map<EEventsDataKeys, Object> data) {

    }

}
