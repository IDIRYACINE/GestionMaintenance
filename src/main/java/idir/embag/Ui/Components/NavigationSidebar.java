package idir.embag.Ui.Components;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Navigation.INavigationController;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
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

    public NavigationSidebar(INavigationController navigationController) {
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
      dispatchNavigationEvent(NavigationStore.SettingsPanelId);
    }
    
    @FXML
    private void sessionClicked() throws IOException{
      setActiveStyle(NavigationStore.SessionPanelId);
      dispatchNavigationEvent(NavigationStore.SessionPanelId);
    }
    @FXML
    private void workersClicked( ) throws IOException{
     setActiveStyle(NavigationStore.WorkersPanelId);
     dispatchNavigationEvent(NavigationStore.WorkersPanelId);
    }
    
    @FXML
    private void stockClicked()throws IOException{
     setActiveStyle(NavigationStore.StockPanelId);
     dispatchNavigationEvent(NavigationStore.StockPanelId);
    }


    @FXML
    private void historyClicked()throws IOException{
     setActiveStyle(NavigationStore.HistoryPanelId);
     dispatchNavigationEvent(NavigationStore.HistoryPanelId);
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

    private void dispatchNavigationEvent(int panelId){
      Map<EEventsDataKeys,Object> data = new HashMap<>();
      DataBundler.bundleNestedData(data, EEventsDataKeys.NavigationKeys, ENavigationKeys.PanelId, panelId);

      StoreEvent event = new StoreEvent(
        EStoreEvents.NavigationEvent,
        EStoreEventAction.Navigation,
        data);

      StoreDispatch dispatch = new StoreDispatch(EStores.NavigationStore,event);

      StoreCenter.getInstance().dispatch(dispatch);
    }


    @Override
    public Node getView() {
      return navigationPanel;
    }

}
