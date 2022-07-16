package idir.embag.Application.Session;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.MangerDialog.SessionWorkersDialog;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class SessionController {
    
    private MFXTableView<SessionRecord> tableRecord;
    
    public void refresh() {
        //TODO : REFRESH
    }

    public void manageSessionGroups() {

        SessionWorkersDialog dialog = new SessionWorkersDialog();
        dialog.loadFxml();
        
        //dialog.setCallbacks();
        Map<EEventDataKeys,Object> data = new HashMap<>();

        data.put(EEventDataKeys.DialogContent, dialog);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }
    
    public void notifyEvent(StoreEvent event) {
       switch(event.getAction()){
              case Add:
                addRecord((SessionRecord) event.getData().get(EEventDataKeys.SessionRecord));
                break;
              case Refresh:
                setRecords((SessionRecord[]) event.getData().get(EEventDataKeys.SessionRecordList));
                break;
              default:
                break;
       }
    }
    
    public void notifyActive(MFXTableView<SessionRecord> tableRecord ) {
        this.tableRecord = tableRecord;
        setColumns();
    }

    public void closeSession() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setMessage(Messages.closeSession);

        dialog.setOnConfirm(data -> {
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.CloseSession, null);
        });
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialog);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    public void export() {
        // TODO : Export to Excel
    }

    private void addRecord(SessionRecord record) {
        tableRecord.getItems().add(record);
    }

    private void setRecords(SessionRecord[] records) {
        tableRecord.getItems().setAll(records);
    }

    private void setColumns(){
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionRecord::getArticleId));
		MFXTableColumn<SessionRecord> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(SessionRecord::getArticleName));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.PriceShift, true, Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> totalPriceShiftColumn = new MFXTableColumn<>(Names.TotalPriceShift, true, Comparator.comparing(SessionRecord::getTotalPriceShift));
		MFXTableColumn<SessionRecord> workerIdColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(SessionRecord::getWorkerId));
        MFXTableColumn<SessionRecord> groupIdColumn = new MFXTableColumn<>(Names.GroupId, true, Comparator.comparing(SessionRecord::getGroupId));
		MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.Date, true, Comparator.comparing(SessionRecord::getDate));
		MFXTableColumn<SessionRecord> inventoryQuantiyColumn = new MFXTableColumn<>(Names.Quantity, true, Comparator.comparing(SessionRecord::getQuantityInventory));
        MFXTableColumn<SessionRecord> stockQuantiyColumn = new MFXTableColumn<>(Names.QuantityShift, true, Comparator.comparing(SessionRecord::getQuantityStock));


        tableRecord.getTableColumns().setAll(idColumn,nameColumn,groupIdColumn,workerIdColumn,dateColumn,
                inventoryQuantiyColumn,stockQuantiyColumn,priceColumn,priceShiftColumn,totalPriceShiftColumn);
        
    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

}