package idir.embag.Ui.Components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import idir.embag.Application.Controllers.Navigation.INavigationController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationSidebar implements Initializable{
    
    @FXML
    private VBox navigationPanel;

    @FXML
    private FontAwesomeIconView sessionIcon , historyIcon,stockIcon ,settingsIcon ;

    @FXML 
    private Label sessionLabel , historyLabel , stockLabel , settingsLabel;

    @FXML
    private HBox sessionBox , historyBox , stockBox , settingsBox;

    private int activePanelIndex = 0;

    private Label[] labels;
    private HBox[] boxes;
    private FontAwesomeIconView[] icons;

    private INavigationController navigationController;

    public void setNavigationController(INavigationController navigationController) {
        this.navigationController = navigationController;
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      icons = new FontAwesomeIconView[INavigationController.PanelCount];
      icons[INavigationController.SessionPanelId] = sessionIcon;
      icons[INavigationController.HistoryPanelId] = historyIcon;
      icons[INavigationController.StockPanelId] = stockIcon;
      icons[INavigationController.SettingsPanelId] = settingsIcon;

      labels = new Label[INavigationController.PanelCount];
      labels[INavigationController.SessionPanelId] = sessionLabel;
      labels[INavigationController.HistoryPanelId] = historyLabel;
      labels[INavigationController.StockPanelId] = stockLabel;
      labels[INavigationController.SettingsPanelId] = settingsLabel;

      boxes = new HBox[INavigationController.PanelCount];
      boxes[INavigationController.SessionPanelId] = sessionBox;
      boxes[INavigationController.HistoryPanelId] = historyBox;
      boxes[INavigationController.StockPanelId] = stockBox;
      boxes[INavigationController.SettingsPanelId] = settingsBox;

    }

     
    @FXML
    private void settingsClicked() throws IOException{
      setActiveStyle(INavigationController.SettingsPanelId);
      navigationController.navigateToSettingsPanel();
    }
    
    @FXML
    private void sessionClicked() throws IOException{
      setActiveStyle(INavigationController.SessionPanelId);
      navigationController.navigateToSessionPanel();
    }
    @FXML
    private void workersClicked( ) throws IOException{
     setActiveStyle(INavigationController.WorkersPanelId);
     navigationController.navigateToWorkersPanel();
    }
    
    @FXML
    private void stockClicked()throws IOException{
     setActiveStyle(INavigationController.StockPanelId);
     navigationController.navigateToStockPanel();
    }


    @FXML
    private void historyClicked()throws IOException{
     setActiveStyle(INavigationController.HistoryPanelId);
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

    public void setup(int panelId,INavigationController navigationController){
      this.navigationController = navigationController;

      String css = getClass().getResource("/css/main.css").toExternalForm();
      navigationPanel.getStylesheets().add(css);

      activePanelIndex = panelId;
      setActiveStyle(activePanelIndex);

    }


}
