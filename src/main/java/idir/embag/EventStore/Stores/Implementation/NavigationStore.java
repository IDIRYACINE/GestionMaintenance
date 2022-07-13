package idir.embag.EventStore.Stores.Implementation;

import idir.embag.Application.Controllers.Navigation.INavigationController;

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

    public void navigateToSettingsPanel(){
        navigationController.navigateToSettingsPanel();
    }

    public void navigateToSessionPanel(){
        navigationController.navigateToSessionPanel();
    }

    public void navigateToHistoryPanel(){
        navigationController.navigateToHistoryPanel();
    }

    public void navigateToWorkersPanel(){
        navigationController.navigateToWorkersPanel();
    }

    public void navigateToStockPanel(){
        navigationController.navigateToStockPanel();
    }

    

}
