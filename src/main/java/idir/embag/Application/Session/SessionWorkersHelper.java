package idir.embag.Application.Session;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionWorkersHelper  {
        
   
    private MFXTableView<SessionWorker> tableSessionWorkers;

    private Map<EStoreEventAction , Consumer<SessionWorker>> callbacks = new HashMap<>();
    
   
    public SessionWorkersHelper() {
        setupCallbacks();
    }

    public void add() {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildAddDialog();
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }
    
    public void update(SessionWorker worker) {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildUpdateDialog();
        data.put(EEventDataKeys.SessionGroupInstance, worker);
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionWorker worker) {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        
        IDialogContent content = buildDeleteDialog();
        data.put(EEventDataKeys.DialogContent, content);
        data.put(EEventDataKeys.SessionGroupInstance, worker);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void notifyEvent(StoreEvent event) {
        callbacks.get(event.getAction()).accept((SessionWorker) event.getData().get(EEventDataKeys.SessionGroupInstance)); 
    }

    
    public void notifyActive(MFXTableView<SessionWorker> tableSessionGroups) {
       this.tableSessionWorkers = tableSessionGroups;
       setColumns();
    }

    private void removeElement(SessionWorker worker){
        tableSessionWorkers.getItems().remove(worker);
    }

    private void addElement(SessionWorker worker){
        tableSessionWorkers.getItems().add(worker);
    }

    private void updateElement(SessionWorker worker){
        int index = tableSessionWorkers.getItems().indexOf(worker);
        tableSessionWorkers.getCell(index).updateRow();
    }

    private IDialogContent buildUpdateDialog(){
        ManagerDialog dialog = new ManagerDialog();
        dialog.setEventKey(EEventDataKeys.AttributeWrappersList);
        
        EEventDataKeys[] attributes = {
            EEventDataKeys.FullName,EEventDataKeys.Password,EEventDataKeys.GroupName
        };

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Update, data);
        });

        return dialog;
    }


    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();
        dialog.setEventKey(EEventDataKeys.AttributeWrappersList);

        EEventDataKeys[] attributes = {
                EEventDataKeys.FullName,EEventDataKeys.Password,EEventDataKeys.GroupName};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Add, data);
        });

        return dialog;
    }

    private IDialogContent buildDeleteDialog(){
        ConfirmationDialog dialog = new ConfirmationDialog();

        dialog.setMessage(Messages.deleteElement);
        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Remove, data);
        });

        return dialog;
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    private void setupCallbacks(){
        callbacks.put(EStoreEventAction.Add, this::addElement);
        callbacks.put(EStoreEventAction.Update, this::updateElement);
        callbacks.put(EStoreEventAction.Remove, this::removeElement);
    }

    private void setColumns(){
        MFXTableColumn<SessionWorker> idColumn = new MFXTableColumn<>(Names.WorkerId, true, Comparator.comparing(SessionWorker::getWorkerId));
		MFXTableColumn<SessionWorker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(SessionWorker::getWorkerName));
        MFXTableColumn<SessionWorker> groupColumn = new MFXTableColumn<>(Names.WorkerEmail, true, Comparator.comparing(SessionWorker::getGroupName));
        MFXTableColumn<SessionWorker> passwordColumn = new MFXTableColumn<>(Names.WorkerPhone, true, Comparator.comparing(SessionWorker::getPassword));
	
        tableSessionWorkers.getTableColumns().setAll(idColumn,groupColumn,nameColumn,passwordColumn);
        
    }


}
