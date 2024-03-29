package idir.embag.EventStore.Stores.StoreCenter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Models.DataConverters.ExcelModel;
import idir.embag.EventStore.Models.History.HistoryModel;
import idir.embag.EventStore.Models.Permissions.GroupPermissionsModel;
import idir.embag.EventStore.Models.Report.ReportModel;
import idir.embag.EventStore.Models.Session.SessionGroupModel;
import idir.embag.EventStore.Models.Session.SessionModel;
import idir.embag.EventStore.Models.Session.SessionWorkersModel;
import idir.embag.EventStore.Models.Stock.FamilyModel;
import idir.embag.EventStore.Models.Stock.InventoryModel;
import idir.embag.EventStore.Models.Stock.StockModel;
import idir.embag.EventStore.Models.Users.DesignationModel;
import idir.embag.EventStore.Models.Users.PermissionsModel;
import idir.embag.EventStore.Models.Users.UsersModel;
import idir.embag.EventStore.Models.Workers.WorkersModel;
import idir.embag.EventStore.Stores.DataConverterStore.DataConverterStore;
import idir.embag.EventStore.Stores.DataStore.DataStore;
import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.Infrastructure.ServicesProvider;
import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;
import idir.embag.Repository.AffectationsRepository;
import idir.embag.Repository.FamilyCodeRepository;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Repository.SessionRepository;
import idir.embag.Repository.StockRepository;
import idir.embag.Repository.UsersRepository;
import idir.embag.Repository.WorkersRepository;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterDelegate;
import idir.embag.Types.Stores.DataConverterStore.IDataConverterStore;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.DataStore.IDataStore;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Types.Stores.StoreCenter.IStoresCenter;
import javafx.application.Platform;

public class StoreCenter implements IStoresCenter {

  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  private static StoreCenter instance;

  private Map<EStores, IStore> stores = new HashMap<>();

  public static StoreCenter getInstance(ServicesProvider servicesCenter, INavigationController navigationController) {
    if (instance == null) {
      instance = new StoreCenter(servicesCenter, navigationController);
    }
    return instance;
  }

  public static StoreCenter getInstance() {
    return instance;
  }

  private StoreCenter(ServicesProvider servicesCenter, INavigationController navigationController) {
    setupStores(servicesCenter.getDatabaseInitialiser());

    stores.put(EStores.NavigationStore, new NavigationStore(navigationController));
  }

  @Override
  public void dispatch(StoreDispatch action) {
    if (action.getStore() != EStores.NavigationStore) {
      executorService.execute(new Runnable() {

        @Override
        public void run() {
          stores.get(action.getStore()).dispatch(action.getEvent());
        }

      });
      return;
    }
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stores.get(action.getStore()).dispatch(action.getEvent());
      }
    });
  }

  @Override
  public void notify(StoreDispatch action) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stores.get(action.getStore()).notifySubscriber(action.getEvent());
      }

    });

  }

  @Override
  public void broadcast(StoreDispatch action) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        stores.get(action.getStore()).broadcast(action.getEvent());
      }

    });
  }

  @Override
  public void subscribeToEvents(EStores store, EStoreEvents storeEvent, IEventSubscriber subscriber) {
    Map<EEventsDataKeys, Object> data = new HashMap<>();
    data.put(EEventsDataKeys.Subscriber, subscriber);

    StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Subscribe, data);

    StoreDispatch action = new StoreDispatch(store, event);
    dispatch(action);
  }

  @Override
  public void unsubscribeFromEvents(EStores store, EStoreEvents storeEvent, IEventSubscriber subscriber) {
    Map<EEventsDataKeys, Object> data = new HashMap<>();
    data.put(EEventsDataKeys.Subscriber, subscriber);

    StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Unsubscribe, data);

    StoreDispatch action = new StoreDispatch(store, event);
    dispatch(action);
  }

  @Override
  public StoreDispatch createStoreEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
      Map<EEventsDataKeys, Object> data) {
    StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
    StoreDispatch action = new StoreDispatch(store, event);

    return action;
  }

  public void dispatchEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
      Map<EEventsDataKeys, Object> data) {
    StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
    StoreDispatch action = new StoreDispatch(store, event);
    StoreCenter.getInstance().dispatch(action);
  }

  public void sendNotifyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
      Map<EEventsDataKeys, Object> data) {
    StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
    StoreDispatch action = new StoreDispatch(store, event);
    StoreCenter.getInstance().notify(action);
  }

  private void setupStores(DatabaseInitialiser databaseInitialiser) {

    StockModel stockModel = new StockModel(databaseInitialiser.getProductQuery(), new StockRepository());

    InventoryRepository inventoryRepository = new InventoryRepository();
    InventoryModel inventoryModel = new InventoryModel(databaseInitialiser.getProductQuery(),
        inventoryRepository);

    FamilyModel familyModel = new FamilyModel(databaseInitialiser.getProductQuery(), new FamilyCodeRepository());
    WorkersModel workersModel = new WorkersModel(databaseInitialiser.getWorkerQuery(), new WorkersRepository());

    SessionRepository sessionRepository = new SessionRepository();

    SessionModel sessionModel = new SessionModel(databaseInitialiser.getSessionQuery(), sessionRepository);

    SessionWorkersModel sessionWorkersModel = new SessionWorkersModel(databaseInitialiser.getSessionQuery(),
        sessionRepository);

    AffectationsRepository designationsRepository = new AffectationsRepository();

    SessionGroupModel sessionGroupModel = new SessionGroupModel(databaseInitialiser.getSessionQuery(),
        sessionRepository, databaseInitialiser.getGroupPermissionsQuery(), designationsRepository);
    HistoryModel historyModel = new HistoryModel(databaseInitialiser.getSessionQuery(), sessionRepository);

    DesignationModel designationModel = new DesignationModel(databaseInitialiser.getAffectationsQuery(),
        designationsRepository);

    PermissionsModel permissionsModel = new PermissionsModel(databaseInitialiser.getAffectationsQuery(),
        databaseInitialiser.getUsersQuery(), designationsRepository);

    UsersModel usersModel = new UsersModel(databaseInitialiser.getUsersQuery(), new UsersRepository(),
        designationsRepository, databaseInitialiser.getAffectationsQuery());

    GroupPermissionsModel groupPermissionsModel = new GroupPermissionsModel(databaseInitialiser.getAffectationsQuery(),
        databaseInitialiser.getGroupPermissionsQuery(), designationsRepository);

    IDataDelegate[] delegates = new IDataDelegate[IDataStore.DELEGATES_COUNT];
    delegates[IDataStore.STOCK_DELEGATE] = stockModel;
    delegates[IDataStore.INVENTORY_DELEGATE] = inventoryModel;
    delegates[IDataStore.HISTORY_DELEGATE] = historyModel;
    delegates[IDataStore.FAMILY_DELEGATE] = familyModel;
    delegates[IDataStore.WORKER_DELEGATE] = workersModel;
    delegates[IDataStore.SESSION_DELEGATE] = sessionModel;
    delegates[IDataStore.SESSION_WORKER_DELEGATE] = sessionWorkersModel;
    delegates[IDataStore.SESSION_GROUP_DELEGATE] = sessionGroupModel;
    delegates[IDataStore.DESIGNATION_DELEGATE] = designationModel;
    delegates[IDataStore.PERMISSION_DELEGATE] = permissionsModel;
    delegates[IDataStore.USER_DELEGATE] = usersModel;
    delegates[IDataStore.GROUP_PERMISSION_DELEGATE] = groupPermissionsModel;

    stores.put(EStores.DataStore, new DataStore(delegates));

    IDataConverterDelegate[] converterDelegates = new IDataConverterDelegate[DataConverterStore.DELEGATES_COUNT];
    converterDelegates[IDataConverterStore.EXCEL_DELEGATE] = new ExcelModel();

    converterDelegates[IDataConverterStore.REPORT_DELEGATE] = new ReportModel(databaseInitialiser.getProductQuery(),
        inventoryRepository);

    stores.put(EStores.DataConverterStore, new DataConverterStore(converterDelegates));
  }

}
