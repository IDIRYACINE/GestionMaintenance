package idir.embag.Application.Controllers.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.Session;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Infrastructure.Server.Api.ApiWrappers.FetchActiveSessionRecordsWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionRecordAttributes;
import idir.embag.Types.Infrastructure.Server.EServerKeys;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Measures;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.MangerDialog.SessionWorkersDialog;
import idir.embag.Ui.Dialogs.ReportDialog.ReportDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class SessionController implements IEventSubscriber {

    private MFXTableView<SessionRecord> tableRecord;

    public static Timestamp sessionId;

    public SessionController() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionEvent, this);
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.SessionRecordsEvent, this);
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

        dispatchEvent(EStores.DataStore, EStoreEvents.SessionRecordsEvent, EStoreEventAction.Load, data);
    }

    public void refreshFromServer() {
        int maxRetrivedRecord = 1000;
        int recordOffset = 0;

        Map<EServerKeys, Object> data = new HashMap<>();

        ArrayList<Integer> permissions = AppState.getInstance().getCurrentUser().getDesignationsIds();

        FetchActiveSessionRecordsWrapper apiWrapper = new FetchActiveSessionRecordsWrapper(maxRetrivedRecord,
                recordOffset, permissions);
        data.put(EServerKeys.ApiWrapper, apiWrapper);

        ApiService.getInstance().getRemoteServer().dispatchApiCall(data);
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
                Session session = DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance);
                setActiveSession(session.getSessionId());
                break;
            case Load:
                if (event.getEvent() == EStoreEvents.SessionRecordsEvent)
                    setRecords(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
                else
                    setActiveSession(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;
            default:
                break;
        }
    }

    private void setActiveSession(Timestamp activeId) {
        sessionId = activeId;

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        StoreEvent event = new StoreEvent(EStoreEvents.SessionEvent, EStoreEventAction.OpenSession, data);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().broadcast(action);

    }

    public void export() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        data.put(EEventsDataKeys.Subscriber, this);

        ReportDialog dialogContent = new ReportDialog();
        dialogContent.setOnConfirm(requestData -> {
            StoreCenter storeCenter = StoreCenter.getInstance();
            StoreDispatch event = storeCenter.createStoreEvent(EStores.DataConverterStore, EStoreEvents.ReportEvent,
                    EStoreEventAction.Export, requestData);
            storeCenter.dispatch(event);
        });

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void addRecord(SessionRecord record) {
        tableRecord.getItems().add(record);
    }

    private void addRecordCollection(Collection<SessionRecord> records) {

        tableRecord.getItems().addAll(records);
    }

    private void setRecords(Collection<SessionRecord> records) {

        tableRecord.getItems().clear();
        tableRecord.getItems().setAll(records);
    }

    private void setColumns() {
        MFXTableColumn<SessionRecord> idColumn = new MFXTableColumn<>(Names.ArticleId, true,
                Comparator.comparing(SessionRecord::getArticleId));
        MFXTableColumn<SessionRecord> nameColumn = new MFXTableColumn<>(Names.ArticleName, true,
                Comparator.comparing(SessionRecord::getArticleName));
        MFXTableColumn<SessionRecord> priceColumn = new MFXTableColumn<>(Names.Price, true,
                Comparator.comparing(SessionRecord::getPrix));
        MFXTableColumn<SessionRecord> workerIdColumn = new MFXTableColumn<>(Names.WorkerName, true,
                Comparator.comparing(SessionRecord::getworkerName));
        MFXTableColumn<SessionRecord> groupIdColumn = new MFXTableColumn<>(Names.GroupId, true,
                Comparator.comparing(SessionRecord::getGroupId));
        MFXTableColumn<SessionRecord> dateColumn = new MFXTableColumn<>(Names.Date, true,
                Comparator.comparing(SessionRecord::getDate));

        idColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getArticleId));
        nameColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getArticleName));
        nameColumn.setMinWidth(Measures.ArticleNameColumnWidth);

        priceColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getPrix));
        priceColumn.setMinWidth(Measures.PriceColumnWidth);

        workerIdColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getworkerName));
        groupIdColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getGroupId));

        dateColumn.setRowCellFactory(record -> new MFXTableRowCell<>(SessionRecord::getDate));
        dateColumn.setMinWidth(Measures.DateColumnWidth);

        tableRecord.getTableColumns().setAll(idColumn, nameColumn, groupIdColumn, workerIdColumn, dateColumn,
                priceColumn);

    }

    private void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().dispatch(action);
    }

    @SuppressWarnings("unused")
    private Collection<AttributeWrapper> sessionRecordToAttributes(SessionRecord record) {
        Collection<AttributeWrapper> attributes = new ArrayList<>();

        attributes.add(new AttributeWrapper(ESessionRecordAttributes.RecordId, record.getRecordId()));
        attributes.add(new AttributeWrapper(ESessionRecordAttributes.SessionId, sessionId));
        attributes.add(new AttributeWrapper(ESessionRecordAttributes.InventoryId, record.getArticleId()));
        attributes.add(new AttributeWrapper(ESessionRecordAttributes.RecordQuantity, record.getQuantityInventory()));
        attributes.add(new AttributeWrapper(ESessionRecordAttributes.QuantityShift, record.getQuantityStock()));
        attributes.add(new AttributeWrapper(ESessionRecordAttributes.WorkerId, record.getworkerName()));

        return attributes;
    }

}