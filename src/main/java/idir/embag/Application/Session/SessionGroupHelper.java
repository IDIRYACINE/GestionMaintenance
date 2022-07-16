package idir.embag.Application.Session;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Metadata.ESessionGroup;
import idir.embag.DataModels.Session.SessionGroup;
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
public class SessionGroupHelper{
    
    private MFXTableView<SessionGroup> tableSessionGroups;

    private Map<EStoreEventAction , Consumer<SessionGroup>> callbacks = new HashMap<>();
    
   
    public SessionGroupHelper() {
        setupCallbacks();
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
        data.put(EEventDataKeys.SessionGroup, group);
        data.put(EEventDataKeys.DialogContent, content);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void delete(SessionGroup group) {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        
        IDialogContent content = buildDeleteDialog();
        data.put(EEventDataKeys.DialogContent, content);
        data.put(EEventDataKeys.SessionGroup, group);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void notifyEvent(StoreEvent event) {
        callbacks.get(event.getAction()).accept((SessionGroup) event.getData().get(EEventDataKeys.SessionGroup)); 
    }

    
    public void notifyActive(MFXTableView<SessionGroup> tableSessionGroups) {
        this.tableSessionGroups = tableSessionGroups;
       setColumns();
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

    private void setColumns(){
        MFXTableColumn<SessionGroup> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionGroup::getId));
		MFXTableColumn<SessionGroup> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionGroup::getName));
       
        tableSessionGroups.getTableColumns().setAll(idColumn,nameColumn);       
    }

    private IDialogContent buildUpdateDialog(){
        ManagerDialog dialog = new ManagerDialog();
        dialog.setEventKey(EEventDataKeys.AttributeWrappersList);

        String[] attributes = EnumAttributesToString(ESessionGroup.values());
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
        
        String[] attributes = EnumAttributesToString(ESessionGroup.values());
        dialog.setAttributes(attributes);

        dialog.loadFxml();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionGroupEvent, EStoreEventAction.Add, data);
        });

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

    private void setupCallbacks(){
        callbacks.put(EStoreEventAction.Add, this::addElement);
        callbacks.put(EStoreEventAction.Update, this::updateElement);
        callbacks.put(EStoreEventAction.Remove, this::removeElement);
    }
    
    
    private String[] EnumAttributesToString(ESessionGroup[] attributes){
        String[] result = new String[attributes.length];
        for (int i = 0 ; i < attributes.length ;i++){
            result[i] = attributes[i].toString();
        }
        return result;
    }

}