package idir.embag.EventStore.Models.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.CloseSessionWrapper;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.OpenSessionWrapper;
import idir.embag.Repository.SessionRepository;
import idir.embag.Types.Generics.EOperationStatus;
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

public class SessionModel implements IDataDelegate {

    ISessionQuery sessionQuery;
    SessionRepository sessionRepository;

    public SessionModel(ISessionQuery sessionQuery, SessionRepository sessionRepository) {
        this.sessionQuery = sessionQuery;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void add(Map<EEventsDataKeys, Object> data) {
        Session session = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);
        openSessionOnServer(session);

        // Collection<AttributeWrapper> wrappers =
        // DataBundler.retrieveNestedValue(data,EEventsDataKeys.WrappersKeys,EWrappers.AttributesCollection);
        // sessionQuery.RegisterSession(wrappers);
        notfiyEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.OpenSession, data);

    }

    @Override
    public void remove(Map<EEventsDataKeys, Object> data) {
        Timestamp sessionId = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);
        closeSessionOnServer(sessionId);

        try {
            sessionQuery.CloseActiveSession(sessionId);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.CloseSession, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {
        try {
            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.AttributesCollection);
            Session session = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            sessionQuery.UpdateSession(session.getSessionId(), wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Update, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {

            SearchWrapper searchParams = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);

            ResultSet result = sessionQuery.SearchSessionRecord(searchParams);
            Collection<Session> sessions = sessionRepository.resultSetToSession(result);

            data.put(EEventsDataKeys.InstanceCollection, sessions);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Search, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        LoadWrapper loadWrapper = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                EWrappers.LoadWrapper);

        try {
            ResultSet rawData = sessionQuery.LoadSessionRecord(loadWrapper);
            Collection<Session> sessions = sessionRepository.resultSetToSession(rawData);

            if (sessions.size() == 0) {
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.NoData);
            } else {
                data.put(EEventsDataKeys.OperationStatus, EOperationStatus.HasData);
            }

            data.put(EEventsDataKeys.InstanceCollection, sessions);
            notfiyEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load, data);
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

    private void openSessionOnServer(Session session) {
        OpenSessionWrapper openSessionWrapper = new OpenSessionWrapper(session);

        Map<EServerKeys, Object> data = new HashMap<>();
        DataBundler.appendData(data, EServerKeys.ApiWrapper, openSessionWrapper);

        IServer server = ServicesProvider.getInstance().getRemoteServer();
        server.dispatchApiCall(data);
    }

    private void closeSessionOnServer(Timestamp sessionId) {
        CloseSessionWrapper closeSessionWrapper = new CloseSessionWrapper(sessionId);

        Map<EServerKeys, Object> data = new HashMap<>();
        DataBundler.appendData(data, EServerKeys.ApiWrapper, closeSessionWrapper);

        IServer server = ServicesProvider.getInstance().getRemoteServer();
        server.dispatchApiCall(data);
    }
}
