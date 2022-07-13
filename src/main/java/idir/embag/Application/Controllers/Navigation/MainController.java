package idir.embag.Application.Controllers.Navigation;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.EventStore.Stores.NavigationStore.NavigationStore;
import idir.embag.Ui.Components.NavigationSidebar;
import idir.embag.Ui.Panels.Generics.INodeView;
import idir.embag.Ui.Panels.Historique.HistoryPanel;
import idir.embag.Ui.Panels.Session.SessionPanel;
import idir.embag.Ui.Panels.Settings.SettingsPanel;
import idir.embag.Ui.Panels.Stock.StockPanel;
import idir.embag.Ui.Panels.Workers.WorkersPanel;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainController  implements Initializable,INavigationController  {

    @FXML
    private HBox rootPanel ;

    @FXML
    private StackPane rightPanel ;

    @FXML
    private VBox leftPanel ;
    
    private INodeView[] views;

    private MFXStageDialog dialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadPanels();
      loadNavigationPane();
    }

    @Override
    public void navigateToPanel(int panelId) {
      Node node = views[panelId].getView();
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

    @Override
    public void displayPopup(Node node) {
      dialog = MFXGenericDialogBuilder.build()
      .setContent(node)
      .setShowAlwaysOnTop(true)
      .toStageDialogBuilder()
      .get();
      
      dialog.show();
    }

    
}
