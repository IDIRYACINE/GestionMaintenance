package idir.embag.Application.Session;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.Editors.SessionWorkerEditor;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionWorkersHelper  implements IEventSubscriber {
        
   
    private MFXTableView<SessionWorker> tableSessionWorkers;
       
    public SessionWorkersHelper() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionWorkerEvent, this);
    }

    public void add() {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildAddDialog();
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }
    
    public void update(SessionWorker worker) {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildUpdateDialog(worker);
        data.put(EEventDataKeys.SessionWorkerInstance, worker);
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionWorker worker) {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        
        IDialogContent content = buildDeleteDialog();
        data.put(EEventDataKeys.DialogContent, content);
        data.put(EEventDataKeys.SessionWorkerInstance, worker);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addElement((SessionWorker)event.getData().get(EEventDataKeys.SessionWorkerInstance));
                break;
            case Remove: removeElement((SessionWorker)event.getData().get(EEventDataKeys.SessionWorkerInstance));
                break;  
            case Update: updateElement((SessionWorker)event.getData().get(EEventDataKeys.SessionWorkerInstance));
                break;
            case Load: setElements((Collection<SessionWorker>)event.getData().get(EEventDataKeys.SessionWorkersCollection));
                break;          
              default:
                   break;
           }
    }

    
    public void notifyActive(MFXTableView<SessionWorker> tableSessionWorkers) {
       this.tableSessionWorkers = tableSessionWorkers;
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

    private void setElements(Collection<SessionWorker> workers){
        tableSessionWorkers.getItems().setAll(workers);
    }

    private IDialogContent buildUpdateDialog(SessionWorker worker){
        SessionWorkerEditor dialog = new SessionWorkerEditor(worker);

        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Update, data);
        });

        return dialog;
    }


    private IDialogContent buildAddDialog(){
        SessionWorker sessionWorker = new SessionWorker(0, "", "", "", 0);

        SessionWorkerEditor dialog = new SessionWorkerEditor(sessionWorker);

        Runnable sucessCallback = () -> {
            addElement(sessionWorker);
        };

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            data.put(EEventDataKeys.OnSucessCallback, sucessCallback);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionWorkerEvent, EStoreEventAction.Add, data);
        });

        dialog.loadFxml();

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

    private void setColumns(){
        MFXTableColumn<SessionWorker> idColumn = new MFXTableColumn<>(Names.WorkerId, true, Comparator.comparing(SessionWorker::getWorkerId));
		MFXTableColumn<SessionWorker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(SessionWorker::getWorkerName));
        MFXTableColumn<SessionWorker> groupColumn = new MFXTableColumn<>(Names.GroupName, true, Comparator.comparing(SessionWorker::getGroupName));
        MFXTableColumn<SessionWorker> passwordColumn = new MFXTableColumn<>(Names.Password, true, Comparator.comparing(SessionWorker::getPassword));
        
        idColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getWorkerId));
        nameColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getWorkerName));
        groupColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getGroupName));
        passwordColumn.setRowCellFactory(worker -> new MFXTableRowCell<>(SessionWorker::getPassword));

        tableSessionWorkers.getTableColumns().setAll(idColumn,groupColumn,nameColumn,passwordColumn);
        
    }


}
