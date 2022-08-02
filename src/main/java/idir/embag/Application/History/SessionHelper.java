package idir.embag.Application.History;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.History.IHistoryHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
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
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionHelper implements  IHistoryHelper, IEventSubscriber {

    private MFXTableView<Session> tableSessions;

    public SessionHelper() {
        //TODO : fix this
        // this.tableSessions = tableSessions;
        // StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionEvent, this);
        // setColumns();
    }

    
    public void notifyActive() {
        setColumns();
        
    }

    private void setColumns(){
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
        
        tableSessions.getTableColumns().setAll(idColumn);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addTableElement((Session)event.getData().get(EEventDataKeys.SessionInstance));
                break;
            case Search: setTableElements((Collection<Session>)event.getData().get(EEventDataKeys.SessionsCollection));
                break;          
            case Load: setTableElements((Collection<Session>)event.getData().get(EEventDataKeys.SessionsCollection));
                break;
              default:
                   break;
           }
        
    }

    @Override
    public void refresh() {
        Map<EEventDataKeys,Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100,0);
        data.put(EEventDataKeys.LoadWrapper, loadWrapper);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load,data);        
    }

    @Override
    public void search() {
        IDialogContent dialogContent =  buildSearchDialog();
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addTableElement(Session Session) {
        tableSessions.getItems().add(Session);
    }

    private void setTableElements(Collection<Session> Sessions){
        tableSessions.getItems().setAll(Sessions);
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