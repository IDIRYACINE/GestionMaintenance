package idir.embag.EventStore.Stores.NavigationStore;

import idir.embag.Application.Controllers.Navigation.INavigationController;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.IDialogContent;

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
        if(event.getAction() == EStoreEventAction.Navigation){
            navigationController.navigateToPanel(((int)event.getData()));
        }
        else{
            navigationController.displayPopup((IDialogContent)event.getData());
        }
    }

    

}
