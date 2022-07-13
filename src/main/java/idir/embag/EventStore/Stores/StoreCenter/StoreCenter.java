package idir.embag.EventStore.Stores.StoreCenter;

import idir.embag.Application.Controllers.Navigation.INavigationController;
import idir.embag.EventStore.Models.History.HistoryModel;
import idir.embag.EventStore.Models.Stock.FamilyModel;
import idir.embag.EventStore.Models.Stock.InventoryModel;
import idir.embag.EventStore.Models.Stock.StockModel;
import idir.embag.EventStore.Stores.DataStore.DataStore;
import idir.embag.EventStore.Stores.DataStore.IDataStore;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.Infrastructure.ServicesCenter;
import idir.embag.Infrastructure.Initialisers.DatabaseInitialiser;

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

    private void setupDataStore(DatabaseInitialiser databaseInitialiser){
      StockModel stockModel = new StockModel(databaseInitialiser.getProductQuery());
      InventoryModel inventoryModel = new InventoryModel(databaseInitialiser.getProductQuery());
      HistoryModel historyModel = new HistoryModel(databaseInitialiser.getSessionQuery());
      FamilyModel familyModel = new FamilyModel(databaseInitialiser.getProductQuery());


      dataStore = new DataStore(stockModel, inventoryModel, historyModel, familyModel);
    }

    private void setupNavigationStore(INavigationController navigationController){
        navigationStore = new NavigationStore(navigationController);
    }



}
