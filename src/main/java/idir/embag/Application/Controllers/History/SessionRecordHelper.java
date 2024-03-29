package idir.embag.Application.Controllers.History;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.History.IHistoryHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Measures;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.FilterDialog.FilterDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.scene.Node;

@SuppressWarnings("unchecked")
public class SessionRecordHelper implements  IHistoryHelper, IEventSubscriber {

    private MFXTableView<SessionRecord> tableSessionRecords;

    public SessionRecordHelper() {
        this.tableSessionRecords =  new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionRecordsEvent, this);
        setup();
    }

    
    public void notifyActive() {
        setup();
    }

    private void setup(){
        tableSessionRecords.setMinWidth(Measures.defaultTablesWidth);
        tableSessionRecords.setMinHeight(Measures.defaultTablesHeight);
        tableSessionRecords.setFooterVisible(false);

        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.SessionRecordId, true, Comparator.comparing(SessionRecord::getRecordId));
		MFXTableColumn<SessionRecord> workerNameColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(SessionRecord::getworkerName));
        MFXTableColumn<SessionRecord> articleIdColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(SessionRecord::getArticleId));
        MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.Date, true, Comparator.comparing(SessionRecord::getDate));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.PriceShift, true, Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> quantityColumn = new MFXTableColumn<>(Names.StockQuantity, true, Comparator.comparing(SessionRecord::getQuantityStock));
        MFXTableColumn<SessionRecord> quantityShiftColumn = new MFXTableColumn<>(Names.QuantityShift, true, Comparator.comparing(SessionRecord::getQuantityShift));
        
        idColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getRecordId));
        workerNameColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getworkerName));
        articleIdColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getArticleId));
        dateColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getDate));
        priceColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getPrix));
        priceShiftColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getPriceShift));
        quantityColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getQuantityStock));
        quantityShiftColumn.setRowCellFactory(session -> new MFXTableRowCell<>(SessionRecord::getQuantityShift));
        
        

        tableSessionRecords.getTableColumns().addAll(idColumn,dateColumn,articleIdColumn,workerNameColumn,priceColumn,priceShiftColumn,quantityColumn,quantityShiftColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addTableElement(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;
            case Search: setTableElements(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;          
            case Load: setTableElements(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
                break;
              default:
                   break;
           }
        
    }

    @Override
    public void refresh() {
        Map<EEventsDataKeys,Object> data = new HashMap<>();

        LoadWrapper loadWrapper = new LoadWrapper(100,0);

        Map<EWrappers,Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Load,data);        
    }

    @Override
    public void search() {
        IDialogContent dialogContent =  buildSearchDialog();
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys,Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(SessionRecord SessionRecord) {
        tableSessionRecords.getItems().add(SessionRecord);
    }

    private void setTableElements(Collection<SessionRecord> SessionRecords){
        tableSessionRecords.getItems().setAll(SessionRecords);
    }

    protected void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
    
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        ESessionRecordAttributes[] attributes = {ESessionRecordAttributes.SessionId, 
            ESessionRecordAttributes.RecordDate};

        dialog.setAttributes(attributes);
        
        dialog.setOnConfirm(new Consumer<Map<EEventsDataKeys,Object>> (){
            @Override
            public void accept(Map<EEventsDataKeys,Object> data) {
                dispatchEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Search, data);
            }
        });
        
        dialog.loadFxml();

        return dialog;

    }


    @Override
    public Node getView() {
        return tableSessionRecords;
    }
    
}