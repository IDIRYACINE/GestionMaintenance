package idir.embag.Application.Controllers.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Ui.Components.NavigationSidebar;
import idir.embag.Ui.Views.Generics.INodeView;
import idir.embag.Ui.Views.Historique.HistoriqueView;
import idir.embag.Ui.Views.Session.SessionView;
import idir.embag.Ui.Views.Settings.SettingsView;
import idir.embag.Ui.Views.Stock.StockView;
import idir.embag.Ui.Views.Workers.WorkersView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
      views[INavigationController.SettingsPanelId] = new SettingsView();
      views[INavigationController.SessionPanelId] = new SessionView();
      views[INavigationController.HistoryPanelId] = new HistoriqueView();
      views[INavigationController.WorkersPanelId] = new WorkersView();
      views[INavigationController.StockPanelId] = new StockView();

      for (INodeView view: views) {
        view.loadFxml();
      }
      
    }

    private void loadNavigationPane(){
     NavigationSidebar navigationSidebar = new NavigationSidebar();
     navigationSidebar.setNavigationController(this);
     navigationSidebar.loadFxml();
     leftPanel.getChildren().setAll(navigationSidebar.getView());
    }
    
}
