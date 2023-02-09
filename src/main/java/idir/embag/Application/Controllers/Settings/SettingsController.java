package idir.embag.Application.Controllers.Settings;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.Controllers.Session.SessionController;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.ExportDialogs.ExportDialog;
import idir.embag.Ui.Dialogs.ExportDialogs.ImportDialog;
import idir.embag.Ui.Dialogs.UsersDialog.UsersManagerDialog;

public class SettingsController {

    public void importData() {
        ImportDialog dialogContent = new ImportDialog();
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent,
                EStoreEventAction.Dialog, data);

        dialogContent.loadFxml();

        storeCenter.dispatch(event);

    }

    public void exportData() {
        ExportDialog dialogContent = new ExportDialog();
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent,
                EStoreEventAction.Dialog, data);

        dialogContent.loadFxml();

        storeCenter.dispatch(event);
    }

    public void manageUsers() {
        UsersManagerDialog dialogContent = new UsersManagerDialog();
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent,
                EStoreEventAction.Dialog, data);

        dialogContent.loadFxml();

        storeCenter.dispatch(event);
    }

    public void openNewSession() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Session session = new Session(timestamp, true, dateString,
                null, 0, 0);
        data.put(EEventsDataKeys.Instance, session);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.AttributesCollection, sessionToAttributes(session));
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Add, data);

    }

    public void loadActiveSessionIfExists() {
        Map<EServerKeys, Object> data = new HashMap<>();

        FetchActiveSessionWrapper apiWrapper = new FetchActiveSessionWrapper();
        data.put(EServerKeys.ApiWrapper, apiWrapper);

        ServicesProvider.getInstance().getRemoteServer().dispatchApiCall(data);

    }

    public void closeSession() {
        ConfirmationDialog dialogContent = new ConfirmationDialog();
        dialogContent.setMessage(Messages.closeSession);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, SessionController.sessionId);

        dialogContent.setOnConfirm(other -> {
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.CloseSession, data);

            sendNotifyEvent(EStores.NavigationStore, EStoreEvents.SessionEvent, EStoreEventAction.CloseSession, data);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    private Collection<AttributeWrapper> sessionToAttributes(Session session) {
        Collection<AttributeWrapper> attributes = new ArrayList<>();

        attributes.add(new AttributeWrapper(ESessionAttributes.SessionId, session.getSessionId()));
        attributes.add(new AttributeWrapper(ESessionAttributes.StartDate, session.getSessionStartDate()));
        attributes.add(new AttributeWrapper(ESessionAttributes.PriceShiftValue, session.getPriceShift()));
        attributes.add(new AttributeWrapper(ESessionAttributes.QuantityShiftValue, session.getQuantityShift()));

        // a weird error in mariadb, it doesn't accept boolean values
        int active = session.isActive() ? 1 : 0;
        attributes.add(new AttributeWrapper(ESessionAttributes.Active, active));

        return attributes;
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    private void sendNotifyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }
}
