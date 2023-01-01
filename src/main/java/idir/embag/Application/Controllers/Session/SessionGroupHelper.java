package idir.embag.Application.Controllers.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Users.Designation;
import idir.embag.EventStore.Models.Permissions.RequestsData.LoadRequest;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.SessionGroupEditor;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionGroupHelper implements IEventSubscriber {

    private MFXTableView<SessionGroup> tableSessionGroups;

    public SessionGroupHelper() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionGroupEvent, this);
    }

    private void handleLoadEvent(StoreEvent event) {
        LoadRequest request = DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance);

        if (request.isLoadGroups()) {
            setElements(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
        }

        else if (request.isLoadGroupUngrantedDesignations()) {
            ArrayList<Designation> designations = DataBundler.retrieveValue(event.getData(),
                    EEventsDataKeys.InstanceCollection);
            updateSessionGroupDialog(request.getGroup(), designations);
        }

        else if (request.isLoadAllDesignations()) {
            Timestamp sessionId = SessionController.sessionId;
            AppState appState = AppState.getInstance();

            SessionGroup group = new SessionGroup(appState.getSessionGroupCurrId(), null, sessionId, null);
            
            ArrayList<Designation> designations = DataBundler.retrieveValue(event.getData(),
                    EEventsDataKeys.InstanceCollection);
            addSessionGroupDialog(group, designations);
        }

    }

    public void add() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        StoreCenter storeCenter = StoreCenter.getInstance();

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(1000, 0);
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.Instance, LoadRequest.loadAllDesignations());
        storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Load, data);
    }

    private void addSessionGroupDialog(SessionGroup group, ArrayList<Designation> designations) {

        SessionGroupEditor dialogContent = new SessionGroupEditor(group, designations);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, group);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Add, requestData);
            AppState.getInstance().nextSessionGroupCurrId();
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    public void update(SessionGroup sessionGroup) {
        SessionGroup group = tableSessionGroups.getSelectionModel().getSelectedValues().get(0);

        if (group != null) {
            Map<EEventsDataKeys, Object> data = new HashMap<>();
            StoreCenter storeCenter = StoreCenter.getInstance();

            data.put(EEventsDataKeys.Subscriber, this);
            data.put(EEventsDataKeys.Instance, LoadRequest.loadGroupUngrantedDesignationsRequest(group));
            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Load, data);

        }
    }

    private void updateSessionGroupDialog(SessionGroup group, ArrayList<Designation> designations) {

        SessionGroupEditor dialogContent = new SessionGroupEditor(group, designations);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, group);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    public void delete(SessionGroup sessionGroup) {
        ConfirmationDialog dialogContent = new ConfirmationDialog();
        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, sessionGroup);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch (event.getAction()) {
            case Add:
                addElement((SessionGroup) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove:
                removeElement((SessionGroup) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Update:
                updateElement((SessionGroup) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Load:
                handleLoadEvent(event);
                break;
            default:
                break;
        }
    }

    public void notifyActive(MFXTableView<SessionGroup> tableSessionGroups) {
        this.tableSessionGroups = tableSessionGroups;
        setColumns();
        refresh();
    }

    private void removeElement(SessionGroup group) {
        tableSessionGroups.getItems().remove(group);
    }

    private void addElement(SessionGroup group) {
        tableSessionGroups.getItems().add(group);
    }

    private void updateElement(SessionGroup group) {
        int index = tableSessionGroups.getItems().indexOf(group);
        tableSessionGroups.getCell(index).updateRow();
    }

    private void setElements(Collection<SessionGroup> groups) {
        tableSessionGroups.getItems().setAll(groups);
    }

    private void refresh() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.Instance, LoadRequest.loadGroups());

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Load, data);
    }

    private void setColumns() {
        MFXTableColumn<SessionGroup> idColumn = new MFXTableColumn<>(Names.GroupId, true,
                Comparator.comparing(SessionGroup::getId));
        MFXTableColumn<SessionGroup> nameColumn = new MFXTableColumn<>(Names.GroupName, true,
                Comparator.comparing(SessionGroup::getName));

        idColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getId));
        nameColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getName));

        tableSessionGroups.getTableColumns().setAll(idColumn, nameColumn);
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

}