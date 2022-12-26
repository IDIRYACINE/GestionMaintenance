package idir.embag.Application.Controllers.Session;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.SessionWorker;
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
import idir.embag.Ui.Components.Editors.SessionWorkerEditor;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionWorkersHelper implements IEventSubscriber {

    private MFXTableView<SessionWorker> tableSessionWorkers;
    private MFXTableView<SessionGroup> tableSessionGroups;

    public SessionWorkersHelper() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionWorkerEvent, this);
    }

    public void add() {

        // SessionWorker worker = new SessionWorker(0, "", "", "", "", 0);

        // SessionWorkerEditor dialogContent = new SessionWorkerEditor(worker);

        // Map<EEventsDataKeys, Object> data = new HashMap<>();

        // Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        // navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        // data.put(EEventsDataKeys.NavigationKeys, navigationData);

        // dialogContent.setOnConfirm(response -> {
        //     response.put(EEventsDataKeys.Instance, worker);
        //     dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Add, response);
        // });

        // dialogContent.loadFxml();

        // dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void update(SessionWorker worker) {
        Collection<SessionGroup> groups = tableSessionGroups.getItems();

        SessionWorkerEditor dialogContent = new SessionWorkerEditor(worker,groups);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(response -> {
            response.put(EEventsDataKeys.Instance, worker);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Update, response);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionWorker worker) {

        ConfirmationDialog dialogContent = new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, worker);

            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void notifyEvent(StoreEvent event) {
        switch (event.getAction()) {
            case Add:
                addElement((SessionWorker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove:
                removeElement((SessionWorker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Update:
                updateElement((SessionWorker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Load:
                setElements((Collection<SessionWorker>) event.getData().get(EEventsDataKeys.InstanceCollection));
                break;
            default:
                break;
        }
    }

    public void notifyActive(MFXTableView<SessionWorker> tableSessionWorkers,
            MFXTableView<SessionGroup> tableSessionGroups) {
        this.tableSessionWorkers = tableSessionWorkers;
        this.tableSessionGroups = tableSessionGroups;
        setColumns();
        refresh();
    }

    private void removeElement(SessionWorker worker) {
        tableSessionWorkers.getItems().remove(worker);
    }

    private void addElement(SessionWorker worker) {
        tableSessionWorkers.getItems().add(worker);
    }

    private void updateElement(SessionWorker worker) {
        int index = tableSessionWorkers.getItems().indexOf(worker);
        tableSessionWorkers.getCell(index).updateRow();
    }

    private void setElements(Collection<SessionWorker> workers) {
        tableSessionWorkers.getItems().setAll(workers);
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    private void setColumns() {
        MFXTableColumn<SessionWorker> idColumn = new MFXTableColumn<>(Names.WorkerId, true,
                Comparator.comparing(SessionWorker::getWorkerId));
        MFXTableColumn<SessionWorker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true,
                Comparator.comparing(SessionWorker::getWorkerName));
        MFXTableColumn<SessionWorker> groupColumn = new MFXTableColumn<>(Names.GroupName, true,
                Comparator.comparing(SessionWorker::getGroupName));
        MFXTableColumn<SessionWorker> usernameColumn = new MFXTableColumn<>(Names.Username, true,
                Comparator.comparing(SessionWorker::getUsername));
        MFXTableColumn<SessionWorker> passwordColumn = new MFXTableColumn<>(Names.Password, true,
                Comparator.comparing(SessionWorker::getPassword));

        idColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getWorkerId));
        nameColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getWorkerName));
        groupColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getGroupName));
        passwordColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getPassword));
        usernameColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getUsername));

        tableSessionWorkers.getTableColumns().setAll(idColumn, usernameColumn, passwordColumn, groupColumn, nameColumn);

    }

    private void refresh() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Load, data);

    }

}
