package idir.embag.Application.Controllers.Session;

import java.util.Comparator;
import java.util.List;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Components.MangerDialog.WorkersManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionController implements ISessionController{
    
    private MFXTableView<SessionRecord> tableRecord;

    public SessionController(MFXTableView<SessionRecord> tableRecord) {
        this.tableRecord = tableRecord;
    }
    
    public void refresh() {}

    @Override
    public void manageSessionWorkers() {
        IDialogContent dialogContent =  buildWorkersManagerDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
    }


    @Override
    public void manageSessionGroups() {
        IDialogContent dialogContent =  buildGroupsManagerDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
    }


    @Override
    public void notifyEvent(StoreEvent event) {

        switch(event.getAction()){
            case Add: addTableElement((SessionRecord)event.getData());
                break;
            case Remove: removeTableElement((int)event.getData());
                break;  
            case Update: updateTableElement();
                break;
            case Search: setTablerecords((List<SessionRecord>)event.getData());
                break;          
              default:
                   break;
           }
        
    }

   
    private void addTableElement(SessionRecord record) {
        tableRecord.getItems().add(record);
    }

    private void removeTableElement(int index){
        tableRecord.getItems().remove(index);
    }

    private void updateTableElement(){
        //TODO : implement
    }

    private void setTablerecords(List<SessionRecord> record){
        tableRecord.getItems().setAll(record);
    }

    private void setColumns(){
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionRecord::getArticleId));
		MFXTableColumn<SessionRecord> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getArticleName));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> totalPriceShiftColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getTotalPriceShift));
		MFXTableColumn<SessionRecord> workerIdColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getWorkerId));
        MFXTableColumn<SessionRecord> groupIdColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getGroupId));
		MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getDate));
		MFXTableColumn<SessionRecord> inventoryQuantiyColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getQuantityInventory));
        MFXTableColumn<SessionRecord> stockQuantiyColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(SessionRecord::getQuantityStock));


        tableRecord.getTableColumns().setAll(idColumn,nameColumn,groupIdColumn,workerIdColumn,dateColumn,
                inventoryQuantiyColumn,stockQuantiyColumn,priceColumn,priceShiftColumn,totalPriceShiftColumn);
        
    }

   
    private IDialogContent buildGroupsManagerDialog(){
        WorkersManagerDialog dialog = new WorkersManagerDialog();
        //TODO: properly handle this
        dialog.loadFxml();

        return dialog;

    }


    private IDialogContent buildWorkersManagerDialog(){
        WorkersManagerDialog dialog = new WorkersManagerDialog();

        dialog.loadFxml();

        return dialog;

    }

    @Override
    public void notifyActive() {
        setColumns();        
    }


}