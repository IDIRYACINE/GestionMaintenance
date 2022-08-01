package idir.embag.Application.Workers;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Workers.IWorkersController;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.Editors.WorkerEditor;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class WorkersController implements IWorkersController , IEventSubscriber {
      
    private MFXTableView<Worker> tableWorkers;

    public WorkersController(MFXTableView<Worker> tableWorkers) {
        this.tableWorkers = tableWorkers;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.WorkersEvent, this);
    }

    @Override
    public void notifyActive() {
        setColumns();
        
    }

    private void setColumns(){
        MFXTableColumn<Worker> idColumn = new MFXTableColumn<>(Names.WorkerId, true, Comparator.comparing(Worker::getId));
		MFXTableColumn<Worker> nameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(Worker::getName));
        MFXTableColumn<Worker> emailColumn = new MFXTableColumn<>(Names.WorkerEmail, true, Comparator.comparing(Worker::getEmail));
        MFXTableColumn<Worker> phoneColumn = new MFXTableColumn<>(Names.WorkerPhone, true, Comparator.comparing(Worker::getPhone));

        tableWorkers.getTableColumns().setAll(idColumn,nameColumn,emailColumn,phoneColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addTableElement((Worker)event.getData().get(EEventDataKeys.WorkerInstance));
                break;
            case Remove: removeTableElement((Worker)event.getData().get(EEventDataKeys.WorkerInstance));
                break;  
            case Update: updateTableElement((Worker)event.getData().get(EEventDataKeys.WorkerInstance));
                break;
            case Search: setTableElements((Collection<Worker>)event.getData().get(EEventDataKeys.WorkersCollection));
                break;          
            case Load: setTableElements((Collection<Worker>)event.getData().get(EEventDataKeys.WorkersCollection));
                break;
              default:
                   break;
           }
        
    }

    @Override
    public void add() {
        Worker worker = new Worker("", 0, "", 0);
        WorkerEditor dialogContent =  new WorkerEditor(worker);

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        Runnable sucessCallback = () -> {
            addTableElement(worker);
        };
        

        dialogContent.setOnConfirm(requestData -> {

            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Add, requestData);
        });

        dialogContent.loadFxml();


        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
 
        
    }

    @Override
    public void refresh() {
        dispatchEvent(EStores.DataStore, EStoreEvents.WorkersEvent, EStoreEventAction.Load,null);        
    }

    @Override
    public void update(Worker worker) {
        WorkerEditor dialogContent =  new WorkerEditor(worker);

        Runnable sucessCallback = () -> {
            updateTableElement(worker);
        };

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void archive(Worker worker) {
        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);
        
        Runnable sucessCallback = () -> {
            removeTableElement(worker);
        };

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();


        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void addWorkerToSession(Worker worker) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void searchWorkers() {
        IDialogContent dialogContent =  buildSearchDialog();
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(Worker worker) {
        tableWorkers.getItems().add(worker);
    }

    private void removeTableElement(Worker worker){
        int index = tableWorkers.getItems().indexOf(worker);
        tableWorkers.getItems().remove(index);
    }

    private void updateTableElement(Worker worker){
        int index = tableWorkers.getItems().indexOf(worker);
        tableWorkers.getCell(index).updateRow();
    }

    private void setTableElements(Collection<Worker> workers){
        tableWorkers.getItems().setAll(workers);
    }

    protected void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
    
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EEventDataKeys[] attributes = {EEventDataKeys.WorkerEmail, 
            EEventDataKeys.WorkerName, EEventDataKeys.WorkerPhone};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }
}
