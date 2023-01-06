package idir.embag.Application.Controllers.Workers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.Application.State.AppState;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Workers.IWorkersController;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionWorkerAttributes;
import idir.embag.Types.Infrastructure.Database.Metadata.EWorkerAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.WorkerEditor;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.FilterDialog.FilterDialog;
import idir.embag.Ui.Dialogs.MangerDialog.AddSessionWorkerDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class WorkersController implements IWorkersController, IEventSubscriber {

    private MFXTableView<Worker> tableWorkers;

    public WorkersController(MFXTableView<Worker> tableWorkers) {
        this.tableWorkers = tableWorkers;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.WorkersEvent, this);
    }

    @Override
    public void notifyActive() {
        setColumns();

    }

    private void setColumns() {
        MFXTableColumn<Worker> idColumn = new MFXTableColumn<>(Names.WorkerId, true,
                Comparator.comparing(Worker::getId));
        MFXTableColumn<Worker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true,
                Comparator.comparing(Worker::getName));
        MFXTableColumn<Worker> emailColumn = new MFXTableColumn<>(Names.WorkerEmail, true,
                Comparator.comparing(Worker::getEmail));
        MFXTableColumn<Worker> phoneColumn = new MFXTableColumn<>(Names.WorkerPhone, true,
                Comparator.comparing(Worker::getPhone));

        idColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(Worker::getId));
        nameColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(Worker::getName));
        emailColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(Worker::getEmail));
        phoneColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(Worker::getPhone));

        tableWorkers.getTableColumns().setAll(idColumn, nameColumn, emailColumn, phoneColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch (event.getAction()) {
            case Add:
                addTableElement((Worker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove:
                removeTableElement((Worker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Update:
                updateTableElement((Worker) event.getData().get(EEventsDataKeys.Instance));
                break;
            case Search:
                if (event.getEvent() == EStoreEvents.SessionGroupEvent) {
                    addSessionWorkerDialog((Worker) event.getData().get(EEventsDataKeys.Instance),
                            (Collection<SessionGroup>) event.getData().get(EEventsDataKeys.InstanceCollection));
                } else {
                    setTableElements((Collection<Worker>) event.getData().get(EEventsDataKeys.InstanceCollection));
                }
                break;
            case Load:
                if (event.getEvent() == EStoreEvents.SessionGroupEvent) {
                    addSessionWorkerDialog((Worker) event.getData().get(EEventsDataKeys.Instance),
                            (Collection<SessionGroup>) event.getData().get(EEventsDataKeys.InstanceCollection));
                } else {
                    setTableElements((Collection<Worker>) event.getData().get(EEventsDataKeys.InstanceCollection));
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void add() {
        Worker worker = new Worker("", 0, "", AppState.getInstance().getWorkerCurrId());
        WorkerEditor dialogContent = new WorkerEditor(worker);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, worker);
            AppState.getInstance().nextWorkerCurrId();
            dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Add, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void refresh() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Load, data);
    }

    @Override
    public void update(Worker worker) {
        WorkerEditor dialogContent = new WorkerEditor(worker);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, worker);
            dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    @Override
    public void archive(Worker worker) {
        ConfirmationDialog dialogContent = new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, worker);

            dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void addWorkerToSession(Worker worker) {
        loadGroups(worker);
    }

    private void addSessionWorkerDialog(Worker worker, Collection<SessionGroup> groups) {
        AddSessionWorkerDialog dialogContent = new AddSessionWorkerDialog(worker, groups);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {

            Map<EWrappers, Object> wrappersData = new HashMap<>();

            SessionWorker sessionWorker = (SessionWorker) requestData.get(EEventsDataKeys.Instance);

            Collection<AttributeWrapper> attributes = sessionWorkerToAttributes(sessionWorker);

            wrappersData.put(EWrappers.AttributesCollection, attributes);

            requestData.put(EEventsDataKeys.WrappersKeys, wrappersData);

            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Add, requestData);

        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void loadGroups(Worker worker) {
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.Instance, worker);

        int supervisorId = AppState.getInstance().getCurrentUser().getUserId();

        ArrayList<AttributeWrapper> params = new ArrayList<>();
        params.add(new AttributeWrapper(ESessionGroupAttributes.GroupSupervisorId, supervisorId));
        SearchWrapper searchWrapper = new SearchWrapper(params);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.SearchWrapper, searchWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Search, data);

    }

    private Collection<AttributeWrapper> sessionWorkerToAttributes(SessionWorker worker) {
        int supervisorId = AppState.getInstance().getCurrentUser().getUserId();

        Collection<AttributeWrapper> result = new ArrayList<>();
        result.add(new AttributeWrapper(EWorkerAttributes.WorkerId, worker.getWorkerId()));
        result.add(new AttributeWrapper(ESessionWorkerAttributes.GroupId, worker.getGroupId()));
        result.add(new AttributeWrapper(ESessionWorkerAttributes.Username, worker.getUsername()));
        result.add(new AttributeWrapper(ESessionWorkerAttributes.Password, String.valueOf((worker.getPassword()))));
        result.add(new AttributeWrapper(ESessionWorkerAttributes.SupervisorId, supervisorId));
        
        return result;
    }

    @Override
    public void searchWorkers() {
        IDialogContent dialogContent = buildSearchDialog();

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(new Consumer<Map<EEventsDataKeys, Object>>() {
            @Override
            public void accept(Map<EEventsDataKeys, Object> data) {
                dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Search, data);
            }
        });

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(Worker worker) {
        tableWorkers.getItems().add(worker);
    }

    private void removeTableElement(Worker worker) {
        int index = tableWorkers.getItems().indexOf(worker);
        tableWorkers.getItems().remove(index);
    }

    private void updateTableElement(Worker worker) {
        int index = tableWorkers.getItems().indexOf(worker);
        tableWorkers.getCell(index).updateRow();
    }

    private void setTableElements(Collection<Worker> workers) {
        tableWorkers.getItems().setAll(workers);
    }

    protected void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    private IDialogContent buildSearchDialog() {
        FilterDialog dialog = new FilterDialog();

        EWorkerAttributes[] attributes = { EWorkerAttributes.WorkerEmail,
                EWorkerAttributes.WorkerName, EWorkerAttributes.WorkerPhone };

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }
}
