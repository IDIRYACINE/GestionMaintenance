package idir.embag.EventStore.Stores.NavigationStore;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class NavigationStore {

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

    public void dispatch(StoreEvent event) {
        Map<EEventDataKeys,Object> data = event.getData();
        
        if(event.getAction() == EStoreEventAction.Navigation){
            navigationController.navigateToPanel((int) data.get(EEventDataKeys.PanelId));
        }
        else{
            navigationController.displayPopup((IDialogContent)data.get(EEventDataKeys.DialogContent));
        }
    }

    

}
