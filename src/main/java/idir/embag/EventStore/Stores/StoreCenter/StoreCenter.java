package idir.embag.EventStore.Stores.StoreCenter;

import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Models.DataConverters.ExcelModel;
import idir.embag.EventStore.Models.History.HistoryModel;
import idir.embag.EventStore.Models.Session.SessionGroupModel;
import idir.embag.EventStore.Models.Session.SessionModel;
import idir.embag.EventStore.Models.Session.SessionWorkersModel;
import idir.embag.EventStore.Models.Stock.FamilyModel;
import idir.embag.EventStore.Models.Stock.InventoryModel;
import idir.embag.EventStore.Models.Stock.StockModel;
import idir.embag.EventStore.Models.Workers.WorkersModel;
import idir.embag.EventStore.Stores.DataConverterStore.DataConverterStore;
import idir.embag.EventStore.Stores.DataStore.DataStore;
import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.Infrastructure.ServicesCenter;
import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;
import idir.embag.Repository.FamilyCodeRepository;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Repository.SessionRepository;
import idir.embag.Repository.StockRepository;
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

public class StoreCenter implements IStoresCenter{

    private static StoreCenter instance;

    private Map<EStores , IStore> stores = new HashMap<>();

    public static StoreCenter getInstance(ServicesCenter servicesCenter,INavigationController navigationController) {
        if (instance == null) {
            instance = new StoreCenter(servicesCenter,navigationController);
        }
        return instance;
    }


    public static StoreCenter getInstance() {
      return instance;
  }


    private StoreCenter(ServicesCenter servicesCenter,INavigationController navigationController) {
        setupDataStore(servicesCenter.getDatabaseInitialiser());
        setupDataConverterStore();
        stores.put(EStores.NavigationStore, new NavigationStore(navigationController));
    }
  
   
    @Override
    public void dispatch(StoreDispatch action) {
     stores.get(action.getStore()).dispatch(action.getEvent());
    }


    @Override
    public void notify(StoreDispatch action) {
      stores.get(action.getStore()).notifySubscriber(action.getEvent());  
    }

    @Override
    public void broadcast(StoreDispatch action) {
      stores.get(action.getStore()).broadcast(action.getEvent());  
    }

    @Override
    public void subscribeToEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber){
      Map<EEventsDataKeys,Object> data = new HashMap<>();
      data.put(EEventsDataKeys.Subscriber, subscriber);

      StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Subscribe, data);

      StoreDispatch action = new StoreDispatch(store,event);
      dispatch(action);
    }

    @Override
    public void unsubscribeFromEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber){
      Map<EEventsDataKeys,Object> data = new HashMap<>();
      data.put(EEventsDataKeys.Subscriber, subscriber);

      StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Unsubscribe, data);

      StoreDispatch action = new StoreDispatch(store,event);
      dispatch(action);
    }
    
    @Override
    public StoreDispatch createStoreEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent, Map<EEventsDataKeys,Object> data) {
      StoreEvent event = new StoreEvent(storeEvent, actionEvent,data);
      StoreDispatch action = new StoreDispatch(store, event);

      return action;
  }

    private void setupDataStore(DatabaseInitialiser databaseInitialiser){
      StockModel stockModel = new StockModel(databaseInitialiser.getProductQuery(), new StockRepository());
      InventoryModel inventoryModel = new InventoryModel(databaseInitialiser.getProductQuery(),new InventoryRepository());
      FamilyModel familyModel = new FamilyModel(databaseInitialiser.getProductQuery(),new FamilyCodeRepository());
      WorkersModel workersModel = new WorkersModel(databaseInitialiser.getWorkerQuery(),new WorkersRepository());

      SessionRepository sessionRepository = new SessionRepository();
      SessionModel sessionModel = new SessionModel(databaseInitialiser.getSessionQuery(),sessionRepository);
      SessionWorkersModel sessionWorkersModel = new SessionWorkersModel(databaseInitialiser.getSessionQuery(),sessionRepository);
      SessionGroupModel sessionGroupModel = new SessionGroupModel(databaseInitialiser.getSessionQuery(),sessionRepository);
      HistoryModel historyModel = new HistoryModel(databaseInitialiser.getSessionQuery(),sessionRepository);

      IDataDelegate[] delegates = new IDataDelegate[IDataStore.DELEGATES_COUNT];
      delegates[IDataStore.STOCK_DELEGATE] = stockModel;
      delegates[IDataStore.INVENTORY_DELEGATE] = inventoryModel;
      delegates[IDataStore.HISTORY_DELEGATE] = historyModel;
      delegates[IDataStore.FAMILY_DELEGATE] = familyModel;
      delegates[IDataStore.WORKER_DELEGATE] = workersModel;
      delegates[IDataStore.SESSION_DELEGATE] = sessionModel;
      delegates[IDataStore.SESSION_WORKER_DELEGATE] = sessionWorkersModel;
      delegates[IDataStore.SESSION_GROUP_DELEGATE] = sessionGroupModel;

      stores.put(EStores.DataStore, new DataStore(delegates));
    }

    private void setupDataConverterStore() {

      IDataConverterDelegate[] delegates = new IDataConverterDelegate[DataConverterStore.DELEGATES_COUNT];
      delegates[IDataConverterStore.EXCEL_DELEGATE] = new ExcelModel();
      
      stores.put(EStores.DataConverterStore, new DataConverterStore(delegates));
    }

}
