package idir.embag.Application.Controllers.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.EventStore.Stores.NavigationStore;
import idir.embag.Ui.Components.NavigationSidebar;
import idir.embag.Ui.Panels.Generics.INodeView;
import idir.embag.Ui.Panels.Historique.HistoryPanel;
import idir.embag.Ui.Panels.Session.SessionPanel;
import idir.embag.Ui.Panels.Settings.SettingsPanel;
import idir.embag.Ui.Panels.Stock.StockPanel;
import idir.embag.Ui.Panels.Workers.WorkersPanel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController extends INavigationController implements Initializable  {

    @FXML
    private HBox rootPanel ;

    @FXML
    private StackPane rightPanel ;

    @FXML
    private VBox leftPanel ;
    
    private INodeView[] views;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadPanels();
      loadNavigationPane();
      navigateToStockPanel();
    }

    @Override
    public void navigateToSettingsPanel() {
      Node node = views[NavigationStore.SettingsPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToSessionPanel() {
      Node node = views[NavigationStore.SessionPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToHistoryPanel() {
      Node node = views[NavigationStore.HistoryPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToWorkersPanel() {
      Node node = views[NavigationStore.WorkersPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToStockPanel() {
      Node node = views[NavigationStore.StockPanelId].getView();
      rightPanel.getChildren().setAll(node);
    }
    
    private void loadPanels(){
      views = new INodeView[NavigationStore.PanelCount];
      views[NavigationStore.SettingsPanelId] = new SettingsPanel();
      views[NavigationStore.SessionPanelId] = new SessionPanel();
      views[NavigationStore.HistoryPanelId] = new HistoryPanel();
      views[NavigationStore.WorkersPanelId] = new WorkersPanel();
      views[NavigationStore.StockPanelId] = new StockPanel();

      for (INodeView view: views) {
        view.loadFxml();
      }
      
    }

    private void loadNavigationPane(){
     NavigationSidebar navigationSidebar = new NavigationSidebar(this);
     navigationSidebar.loadFxml();
     leftPanel.getChildren().setAll(navigationSidebar.getView());
    }
    
}
