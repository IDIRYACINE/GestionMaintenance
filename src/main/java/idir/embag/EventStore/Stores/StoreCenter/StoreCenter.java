package idir.embag.EventStore.Stores.StoreCenter;

import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Models.History.HistoryModel;
import idir.embag.EventStore.Models.Stock.FamilyModel;
import idir.embag.EventStore.Models.Stock.InventoryModel;
import idir.embag.EventStore.Models.Stock.StockModel;
import idir.embag.EventStore.Stores.DataStore.DataStore;
import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.Infrastructure.ServicesCenter;
import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;
import idir.embag.Repository.FamilyCodeRepository;
import idir.embag.Repository.InventoryRepository;
import idir.embag.Repository.StockRepository;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.Stores.DataStore.IDataStore;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Types.Stores.StoreCenter.IStoresCenter;

public class StoreCenter implements IStoresCenter{

    private static StoreCenter instance;
    

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
        setupNavigationStore(navigationController);
    }

    private IDataStore dataStore;
    private NavigationStore navigationStore;
  
    @Override
    public void dispatch(StoreDispatch action) {
      if(action.getStore() == EStores.NavigationStore){
        navigationStore.dispatch(action.getEvent());
      }
      else{
        dataStore.dispatch(action.getEvent());
      }
        
    }

    @Override
    public void notify(StoreDispatch action) {
      dataStore.notifySubscribers(action.getEvent());  
    }

    @Override
    public void subscribeToEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber){
      Map<EEventDataKeys,Object> data = new HashMap<>();
      data.put(EEventDataKeys.Subscriber, subscriber);

      StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Subscribe, data);

      StoreDispatch action = new StoreDispatch(store,event);
      dispatch(action);
    }

    @Override
    public void unsubscribeFromEvents(EStores store ,EStoreEvents storeEvent, IEventSubscriber subscriber){
      Map<EEventDataKeys,Object> data = new HashMap<>();
      data.put(EEventDataKeys.Subscriber, subscriber);

      StoreEvent event = new StoreEvent(storeEvent, EStoreEventAction.Unsubscribe, data);

      StoreDispatch action = new StoreDispatch(store,event);
      dispatch(action);
    }

    private void setupDataStore(DatabaseInitialiser databaseInitialiser){
      StockModel stockModel = new StockModel(databaseInitialiser.getProductQuery(), new StockRepository());
      InventoryModel inventoryModel = new InventoryModel(databaseInitialiser.getProductQuery(),new InventoryRepository());
      HistoryModel historyModel = new HistoryModel(databaseInitialiser.getSessionQuery());

      FamilyModel familyModel = new FamilyModel(databaseInitialiser.getProductQuery(),new FamilyCodeRepository());


      dataStore = new DataStore(stockModel, inventoryModel, historyModel, familyModel);
    }

    private void setupNavigationStore(INavigationController navigationController){
        navigationStore = new NavigationStore(navigationController);
    }



}
