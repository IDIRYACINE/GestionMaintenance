package idir.embag.Application.Controllers.Navigation;

public abstract class INavigationController {

    public static final int PanelCount = 5;
    public static final int SettingsPanelId = 0;
    public static final int SessionPanelId = 1;
    public static final int HistoryPanelId = 2;
    public static final int WorkersPanelId = 3;
    public static final int StockPanelId = 4;
    
    public abstract void navigateToSettingsPanel();

    public abstract void navigateToSessionPanel();

    public abstract void navigateToHistoryPanel();

    public abstract void navigateToWorkersPanel();

    public abstract void navigateToStockPanel();

    
}
