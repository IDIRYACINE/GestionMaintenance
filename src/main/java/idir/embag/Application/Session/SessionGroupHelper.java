package idir.embag.Application.Session;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
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
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionGroupHelper implements IEventSubscriber{
    
    private MFXTableView<SessionGroup> tableSessionGroups;
    
    public SessionGroupHelper() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionGroupEvent, this);
    }

    public void add() {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildAddDialog();
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }
    
    public void update(SessionGroup group) {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        IDialogContent content = buildUpdateDialog();
        data.put(EEventDataKeys.SessionGroupInstance, group);
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionGroup group) {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        
        IDialogContent content = buildDeleteDialog();
        data.put(EEventDataKeys.DialogContent, content);
        data.put(EEventDataKeys.SessionGroupInstance, group);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addElement((SessionGroup)event.getData().get(EEventDataKeys.SessionGroupInstance));
                break;
            case Remove: removeElement((SessionGroup)event.getData().get(EEventDataKeys.SessionGroupInstance));
                break;  
            case Update: updateElement((SessionGroup)event.getData().get(EEventDataKeys.SessionGroupInstance));
                break;
            case Load: setElements((Collection<SessionGroup>)event.getData().get(EEventDataKeys.SessionGroupCollection));
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

    private void removeElement(SessionGroup group){
        tableSessionGroups.getItems().remove(group);
    }

    private void addElement(SessionGroup group){
        tableSessionGroups.getItems().add(group);
    }

    private void updateElement(SessionGroup group){
        int index = tableSessionGroups.getItems().indexOf(group);
        tableSessionGroups.getCell(index).updateRow();
    }
    private void setElements(Collection<SessionGroup> groups){
        tableSessionGroups.getItems().setAll(groups);
    }

    private void refresh(){
        dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Load, null);
    }

    private void setColumns(){
        MFXTableColumn<SessionGroup> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionGroup::getId));
		MFXTableColumn<SessionGroup> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionGroup::getName));
        
        idColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getId));
        nameColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getName));

        tableSessionGroups.getTableColumns().setAll(idColumn,nameColumn);       
    }

    private IDialogContent buildUpdateDialog(){
        ManagerDialog dialog = new ManagerDialog();
        dialog.setEventKey(EEventDataKeys.AttributeWrappersList);

        EEventDataKeys[] attributes = {
            EEventDataKeys.SessionGroupName
        };

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Update, data);
        });

        return dialog;
    }


    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();
        dialog.setEventKey(EEventDataKeys.AttributeWrappersList);
        
        EEventDataKeys[] attributes = {
            EEventDataKeys.SessionGroupName
        };

        dialog.setAttributes(attributes);

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Add, data);
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
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Remove, data);
        });

        return dialog;
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

   
    
    
}