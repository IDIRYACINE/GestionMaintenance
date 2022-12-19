package idir.embag.Application.Controllers.Session;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionRecordsWrapper;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionAttributes;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.MangerDialog.SessionWorkersDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionController implements IEventSubscriber {

    private MFXTableView<SessionRecord> tableRecord;

    //TODO : fix this too messy
    public static  Session activeSession;

    public SessionController() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionEvent, this);
    }

    public void setup(MFXTableView<SessionRecord> tableRecord) {
        this.tableRecord = tableRecord;
        setColumns();
    }

    public void refreshFromLocalDb() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load, data);
    }

    public void refreshFromServer() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Load, data);
    }

    public void manageSessionGroups() {

        SessionWorkersDialog dialogContent = new SessionWorkersDialog();

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch (event.getAction()) {
            case Add:
                addRecord(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;
            case AddCollection:
                addRecordCollection(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
                break;
            case Refresh:
                setRecords(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
                break;
            case OpenSession:
                setActiveSession(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
            default:
                break;
        }
    }

    public void closeSession() {
        ConfirmationDialog dialogContent = new ConfirmationDialog();
        dialogContent.setMessage(Messages.closeSession);
        // TODO: save session records to local db
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        dialogContent.setOnConfirm(other -> {
            dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.CloseSession, data);
        });

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);
        data.put(EEventsDataKeys.Instance, activeSession);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    private void setActiveSession(Session session) {
        activeSession = session;
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        StoreEvent event = new StoreEvent(EStoreEvents.SessionEvent, EStoreEventAction.OpenSession, data);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().broadcast(action);

    }

    public void export() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        EStoreEvents targetTable = EStoreEvents.SessionRecordsEvent;
        LoadWrapper loadWrapper = new LoadWrapper(1000, 0);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        ExportWrapper exportWrapper = new ExportWrapper(loadWrapper, targetTable);
        wrappersData.put(EWrappers.ExportWrapper, exportWrapper);

        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        data.put(EEventsDataKeys.InstanceCollection, tableRecord.getItems());

        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, targetTable,
                EStoreEventAction.Export, data);
        storeCenter.dispatch(event);
    }

    private void addRecord(SessionRecord record) {
        tableRecord.getItems().add(record);
    }

    private void addRecordCollection(Collection<SessionRecord> records) {
        tableRecord.getItems().addAll(records);
    }

    private void setRecords(Collection<SessionRecord> records) {
        tableRecord.getItems().setAll(records);
    }

    private void setColumns() {
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.ArticleId, true,
                Comparator.comparing(SessionRecord::getArticleId));
        MFXTableColumn<SessionRecord> nameColumn = new MFXTableColumn<>(Names.ArticleName, true,
                Comparator.comparing(SessionRecord::getArticleName));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.Price, true,
                Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> priceShiftColumn = new MFXTableColumn<>(Names.PriceShift, true,
                Comparator.comparing(SessionRecord::getPriceShift));
        MFXTableColumn<SessionRecord> workerIdColumn = new MFXTableColumn<>(Names.WorkerName, true,
                Comparator.comparing(SessionRecord::getworkerName));
        MFXTableColumn<SessionRecord> groupIdColumn = new MFXTableColumn<>(Names.GroupId, true,
                Comparator.comparing(SessionRecord::getGroupId));
        MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.Date, true,
                Comparator.comparing(SessionRecord::getDate));
        MFXTableColumn<SessionRecord> inventoryQuantiyColumn = new MFXTableColumn<>(Names.Quantity, true,
                Comparator.comparing(SessionRecord::getQuantityInventory));
        MFXTableColumn<SessionRecord> stockQuantiyColumn = new MFXTableColumn<>(Names.QuantityShift, true,
                Comparator.comparing(SessionRecord::getQuantityStock));

        idColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getRecordId));
        nameColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getArticleName));
        priceColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getPrix));
        priceShiftColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getPriceShift));
        workerIdColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getworkerName));
        groupIdColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getGroupId));
        dateColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getDate));
        inventoryQuantiyColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getQuantityInventory));
        stockQuantiyColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getQuantityStock));

        tableRecord.getTableColumns().setAll(idColumn, nameColumn, groupIdColumn, workerIdColumn, dateColumn,
                inventoryQuantiyColumn, stockQuantiyColumn, priceColumn, priceShiftColumn);

    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    public void openNewSession() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Session session = new Session(timestamp, true, dateString,
                null, 0, 0);
        data.put(EEventsDataKeys.Instance, session);

        Map<EWrappers, Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.AttributesCollection, sessionToAttributes(session));
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionEvent, EStoreEventAction.Add, data);

    }

    public void loadActiveSessionIfExists() {
        Map<EServerKeys, Object> data = new HashMap<>();

        FetchActiveSessionRecordsWrapper apiWrapper = new FetchActiveSessionRecordsWrapper(500, 0);
        data.put(EServerKeys.ApiWrapper, apiWrapper);

        ServicesProvider.getInstance().getRemoteServer().dispatchApiCall(data);

    }

    private Collection<AttributeWrapper> sessionToAttributes(Session session) {
        Collection<AttributeWrapper> attributes = new ArrayList<>();

        attributes.add(new AttributeWrapper(ESessionAttributes.SessionId, session.getSessionId()));
        attributes.add(new AttributeWrapper(ESessionAttributes.StartDate, session.getSessionStartDate()));
        attributes.add(new AttributeWrapper(ESessionAttributes.PriceShiftValue, session.getPriceShift()));
        attributes.add(new AttributeWrapper(ESessionAttributes.QuantityShiftValue, session.getQuantityShift()));

        // a weird error in mariadb, it doesn't accept boolean values
        int active = session.isActive() ? 1 : 0;
        attributes.add(new AttributeWrapper(ESessionAttributes.Active, active));

        return attributes;
    }

}