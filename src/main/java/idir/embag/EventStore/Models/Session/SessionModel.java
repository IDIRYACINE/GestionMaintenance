package idir.embag.EventStore.Models.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Session.ISessionHelper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.MangerDialog.SessionWorkersDialog;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionModel implements ISessionHelper{
    
    private MFXTableView<SessionRecord> tableRecord;

    public void setTable(MFXTableView<SessionRecord> tableRecord) {
        this.tableRecord = tableRecord;
    }

    @Override
    public void manageSessionWorkers() {
        IDialogContent dialogContent =  buildWorkersManagerDialog();
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,data);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
        
    }

    @Override
    public void manageSessionGroups() {
        IDialogContent dialogContent =  buildGroupsManagerDialog();
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,data);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
        
    }

    @Override
    public void refresh() {}

    @Override
    public void notifyActive() {}

    @Override
    public void closeSession() {}

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
           case Add:
               addTableElement((SessionRecord) event.getData());
               break;
            case Refresh:
                setTablerecords((List<SessionRecord>) event.getData());;
                break;
            default:
                break;      
        }
    }

    private void setTablerecords(List<SessionRecord> record){
        tableRecord.getItems().setAll(record);
    }


    private IDialogContent buildGroupsManagerDialog(){
        SessionWorkersDialog dialog = new SessionWorkersDialog();
        //TODO: properly handle this
        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildWorkersManagerDialog(){
        SessionWorkersDialog dialog = new SessionWorkersDialog();

        
        dialog.loadFxml();

        return dialog;
    }
    

    private void addTableElement(SessionRecord record) {
        tableRecord.getItems().add(record);
    }


}
