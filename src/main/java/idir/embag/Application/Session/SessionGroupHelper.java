package idir.embag.Application.Session;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.Editors.SessionGroupEditor;
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
        SessionGroup sessionGroup = new SessionGroup(0,"",0);

        SessionGroupEditor dialog = new SessionGroupEditor(sessionGroup);

        Runnable sucessCallback = () -> {
            addElement(sessionGroup);
        };

        dialog.setOnConfirm(requestData -> {
            requestData.remove(EEventDataKeys.DialogContent);
            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Add, requestData);
        });

        data.put(EEventDataKeys.DialogContent, dialog);

        dialog.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }
    
    public void update(SessionGroup group) {
        Map<EEventDataKeys,Object> data = new HashMap<>();

        SessionGroupEditor dialog = new SessionGroupEditor(group);

        Runnable sucessCallback = () -> {
            updateElement(group);
        };

        dialog.setOnConfirm(response -> {
            response.remove(EEventDataKeys.DialogContent);
            response.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Update, response);
        });
        
        data.put(EEventDataKeys.DialogContent, dialog);
        dialog.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionGroup group) {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setMessage(Messages.deleteElement);

        data.put(EEventDataKeys.DialogContent, dialog);
        data.put(EEventDataKeys.SessionGroupInstance, group);

        dialog.setOnConfirm(otherData -> {
            otherData.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Remove, data);
        });

        dialog.loadFxml();

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
        MFXTableColumn<SessionGroup> idColumn = new MFXTableColumn<>(Names.GroupId, true, Comparator.comparing(SessionGroup::getId));
		MFXTableColumn<SessionGroup> nameColumn = new MFXTableColumn<>(Names.GroupName, true, Comparator.comparing(SessionGroup::getName));
        
        idColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getId));
        nameColumn.setRowCellFactory(group -> new MFXTableRowCell<>(SessionGroup::getName));

        tableSessionGroups.getTableColumns().setAll(idColumn,nameColumn);       
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

   
    
    
}