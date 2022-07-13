package idir.embag.Ui.Components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import idir.embag.Application.Controllers.Navigation.INavigationController;
import idir.embag.EventStore.Stores.Implementation.NavigationStore;
import idir.embag.Ui.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationSidebar extends INodeView  implements Initializable{
    
    @FXML
    private VBox navigationPanel;

    @FXML
    private FontAwesomeIconView sessionIcon , historyIcon,stockIcon ,settingsIcon ,workersIcon;

    @FXML 
    private Label sessionLabel , historyLabel , stockLabel , settingsLabel,workersLabel;

    @FXML
    private HBox sessionBox , historyBox , stockBox , settingsBox,workersBox;

    private int activePanelIndex = 0;

    private Label[] labels;
    private HBox[] boxes;
    private FontAwesomeIconView[] icons;

    private INavigationController navigationController;
    
    public NavigationSidebar(INavigationController navigationController) {
      this.navigationController = navigationController;
      fxmlPath = "/views/Components/NavigationSidebar.fxml";
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
      icons = new FontAwesomeIconView[NavigationStore.PanelCount];
      icons[NavigationStore.SessionPanelId] = sessionIcon;
      icons[NavigationStore.HistoryPanelId] = historyIcon;
      icons[NavigationStore.StockPanelId] = stockIcon;
      icons[NavigationStore.SettingsPanelId] = settingsIcon;
      icons[NavigationStore.WorkersPanelId] = workersIcon;

      labels = new Label[NavigationStore.PanelCount];
      labels[NavigationStore.SessionPanelId] = sessionLabel;
      labels[NavigationStore.HistoryPanelId] = historyLabel;
      labels[NavigationStore.StockPanelId] = stockLabel;
      labels[NavigationStore.SettingsPanelId] = settingsLabel;
      labels[NavigationStore.WorkersPanelId] = workersLabel;

      boxes = new HBox[NavigationStore.PanelCount];
      boxes[NavigationStore.SessionPanelId] = sessionBox;
      boxes[NavigationStore.HistoryPanelId] = historyBox;
      boxes[NavigationStore.StockPanelId] = stockBox;
      boxes[NavigationStore.SettingsPanelId] = settingsBox;
      boxes[NavigationStore.WorkersPanelId] = workersBox;
    }

     
    @FXML
    private void settingsClicked() throws IOException{
      setActiveStyle(NavigationStore.SettingsPanelId);
      navigationController.navigateToSettingsPanel();
    }
    
    @FXML
    private void sessionClicked() throws IOException{
      setActiveStyle(NavigationStore.SessionPanelId);
      navigationController.navigateToSessionPanel();
    }
    @FXML
    private void workersClicked( ) throws IOException{
     setActiveStyle(NavigationStore.WorkersPanelId);
     navigationController.navigateToWorkersPanel();
    }
    
    @FXML
    private void stockClicked()throws IOException{
     setActiveStyle(NavigationStore.StockPanelId);
     navigationController.navigateToStockPanel();
    }


    @FXML
    private void historyClicked()throws IOException{
     setActiveStyle(NavigationStore.HistoryPanelId);
     navigationController.navigateToHistoryPanel();
    }

    private void setActiveStyle(int index){
    
      setPassiveStyle(activePanelIndex);

      labels[index].setStyle("-fx-text-fill:#3683dc;");
      icons[index].setStyle("-fx-fill:#3683dc;");
      boxes[index].setStyle("-fx-background-color: #eaf4fe;");

      activePanelIndex = index;
     
    }

    private void setPassiveStyle(int index){
      labels[index].setStyle("-fx-text-fill:#818893;");
      icons[index].setStyle("-fx-fill:#818893;");
      boxes[index].setStyle("-fx-background-color: ffffff");
    }

    public void setup(int panelId){
    
      String css = getClass().getResource("/css/main.css").toExternalForm();
      navigationPanel.getStylesheets().add(css);

      activePanelIndex = panelId;
      setActiveStyle(activePanelIndex);

    }


    @Override
    public Node getView() {
      return navigationPanel;
    }

}
