package idir.embag.Application.Controllers.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

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
      Node node = views[INavigationController.SettingsPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToSessionPanel() {
      Node node = views[INavigationController.SessionPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToHistoryPanel() {
      Node node = views[INavigationController.HistoryPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToWorkersPanel() {
      Node node = views[INavigationController.WorkersPanelId].getView();
      rightPanel.getChildren().setAll(node);
      
    }

    @Override
    public void navigateToStockPanel() {
      Node node = views[INavigationController.StockPanelId].getView();
      rightPanel.getChildren().setAll(node);
    }
    
    private void loadPanels(){
      views = new INodeView[INavigationController.PanelCount];
      views[INavigationController.SettingsPanelId] = new SettingsPanel();
      views[INavigationController.SessionPanelId] = new SessionPanel();
      views[INavigationController.HistoryPanelId] = new HistoryPanel();
      views[INavigationController.WorkersPanelId] = new WorkersPanel();
      views[INavigationController.StockPanelId] = new StockPanel();

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
