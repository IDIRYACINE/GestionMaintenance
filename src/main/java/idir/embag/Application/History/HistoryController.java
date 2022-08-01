package idir.embag.Application.History;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.History.IHistoryController;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class HistoryController implements  IHistoryController, IEventSubscriber {
      
    private MFXTableView<SessionRecord> tableSessionRecords;

    public HistoryController(MFXTableView<SessionRecord> tableSessionRecords) {
        this.tableSessionRecords = tableSessionRecords;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionRecordsEvent, this);
    }

    
    public void notifyActive() {
        setColumns();
        
    }

    private void setColumns(){
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.SessionRecordId, true, Comparator.comparing(SessionRecord::getRecordId));
		MFXTableColumn<SessionRecord> workerNameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(SessionRecord::getworkerName));
        MFXTableColumn<SessionRecord> articleIdColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionRecord::getArticleId));
        MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.Date, true, Comparator.comparing(SessionRecord::getDate));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.PriceShift, true, Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> quantityColumn = new MFXTableColumn<>(Names.StockQuantity, true, Comparator.comparing(SessionRecord::getQuantityStock));
        MFXTableColumn<SessionRecord> quantityShiftColumn = new MFXTableColumn<>(Names.QuantityShift, true, Comparator.comparing(SessionRecord::getQuantityShift));

        tableSessionRecords.getTableColumns().setAll(idColumn,dateColumn,articleIdColumn,workerNameColumn,priceColumn,priceShiftColumn,quantityColumn,quantityShiftColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addTableElement((SessionRecord)event.getData().get(EEventDataKeys.SessionRecordInstance));
                break;
            case Remove: removeTableElement((SessionRecord)event.getData().get(EEventDataKeys.SessionRecordInstance));
                break;  
            case Update: updateTableElement((SessionRecord)event.getData().get(EEventDataKeys.SessionRecordInstance));
                break;
            case Search: setTableElements((Collection<SessionRecord>)event.getData().get(EEventDataKeys.SessionRecordsCollection));
                break;          
            case Load: setTableElements((Collection<SessionRecord>)event.getData().get(EEventDataKeys.SessionRecordsCollection));
                break;
              default:
                   break;
           }
        
    }

    @Override
    public void refresh() {
        dispatchEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Load,null);        
    }


    @Override
    public void searchRecords() {
        IDialogContent dialogContent =  buildSearchDialog();
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(SessionRecord SessionRecord) {
        tableSessionRecords.getItems().add(SessionRecord);
    }

    private void removeTableElement(SessionRecord SessionRecord){
        int index = tableSessionRecords.getItems().indexOf(SessionRecord);
        tableSessionRecords.getItems().remove(index);
    }

    private void updateTableElement(SessionRecord SessionRecord){
        int index = tableSessionRecords.getItems().indexOf(SessionRecord);
        tableSessionRecords.getCell(index).updateRow();
    }

    private void setTableElements(Collection<SessionRecord> SessionRecords){
        tableSessionRecords.getItems().setAll(SessionRecords);
    }

    protected void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
    
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EEventDataKeys[] attributes = {EEventDataKeys.SessionId, 
            EEventDataKeys.SessionDate};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }
}
