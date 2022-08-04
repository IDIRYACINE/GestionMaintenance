package idir.embag.EventStore.Stores.NavigationStore;

import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class NavigationStore implements IStore {

    public static final int PanelCount = 5;
    public static final int SettingsPanelId = 0;
    public static final int SessionPanelId = 1;
    public static final int HistoryPanelId = 2;
    public static final int WorkersPanelId = 3;
    public static final int StockPanelId = 4;

    private INavigationController navigationController;
    
    public NavigationStore(INavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @Override
    public void dispatch(StoreEvent event) {
        Map<EEventsDataKeys,Object> data = event.getData();
        
        if(event.getAction() == EStoreEventAction.Navigation){
            navigationController.navigateToPanel(DataBundler.retrieveNestedValue(data,EEventsDataKeys.NavigationKeys,ENavigationKeys.PanelId));
        }
        else{

            navigationController.displayPopup(DataBundler.retrieveNestedValue(data,EEventsDataKeys.NavigationKeys,ENavigationKeys.DialogContent));
        }
    }

    @Override
    public void notifySubscriber(StoreEvent event) {
        
    }

    @Override
    public void broadcast(StoreEvent event) {
        
    }

    @Override
    public void subscribe(StoreEvent event) {
        
    }

    @Override
    public void unsubscribe(StoreEvent event) {
        
    }

    

}
