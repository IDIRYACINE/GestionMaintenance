package idir.embag.Application.Controllers.History;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.History.IHistoryHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
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
public class SessionHelper implements  IHistoryHelper, IEventSubscriber {

    private MFXTableView<Session> tableSessions;

    public SessionHelper() {
        tableSessions = new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionEvent, this);
        setup();
    }

    
    public void notifyActive() {
        setup();
        
    }

    private void setup(){
        tableSessions.setMinWidth(Measures.defaultTablesWidth);
        tableSessions.setMinHeight(Measures.defaultTablesHeight);
        tableSessions.setFooterVisible(false);


        MFXTableColumn<Session> idColumn = new MFXTableColumn<>(Names.SessionId, true, Comparator.comparing(Session::getSessionId));
		MFXTableColumn<Session> startDateColumn = new MFXTableColumn<>(Names.WorkerName, true, Comparator.comparing(Session::getSessionStartDate));
        MFXTableColumn<Session> endDateColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(Session::getSessionEndDate));
        MFXTableColumn<Session> quantityShiftColumn = new MFXTableColumn<>(Names.Date, true, Comparator.comparing(Session::getQuantityShift));
        MFXTableColumn<Session> priceShiftColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(Session::getPriceShift));
        
        idColumn.setRowCellFactory(session -> new MFXTableRowCell<>(Session::getSessionId));
        startDateColumn.setRowCellFactory(session -> new MFXTableRowCell<>(Session::getSessionStartDate));
        endDateColumn.setRowCellFactory(session -> new MFXTableRowCell<>(Session::getSessionEndDate));
        quantityShiftColumn.setRowCellFactory(session -> new MFXTableRowCell<>(Session::getQuantityShift));
        priceShiftColumn.setRowCellFactory(session -> new MFXTableRowCell<>(Session::getPriceShift));
        
        tableSessions.getTableColumns().setAll(idColumn,startDateColumn,endDateColumn,quantityShiftColumn,priceShiftColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {

        switch(event.getAction()){
            case Add: addTableElement((Session)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Search: setTableElements((Collection<Session>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;          
            case Load: setTableElements((Collection<Session>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;
              default:
                   break;
           }
        
    }

    @Override
    public void refresh() {
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100,0);
        
        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load,data);        
    }

    @Override
    public void search() {
        
        IDialogContent dialogContent =  buildSearchDialog();
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(Session Session) {
        tableSessions.getItems().add(Session);
    }

    private void setTableElements(Collection<Session> sessions){
        if(sessions != null)
            tableSessions.getItems().setAll(sessions);
    }

    protected void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }
    
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        ESessionAttributes[] attributes = {ESessionAttributes.SessionId, 
            ESessionAttributes.StartDate};

        dialog.setAttributes(attributes);
        
        dialog.setOnConfirm(new Consumer<Map<EEventsDataKeys,Object>> (){
            @Override
            public void accept(Map<EEventsDataKeys,Object> data) {
                dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Search, data);
            }
        });

        dialog.loadFxml();

        return dialog;

    }


    @Override
    public Node getView() {
        return tableSessions;
    }
    
}