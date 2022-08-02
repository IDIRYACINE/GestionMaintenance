package idir.embag.Ui.Panels.Settings;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Components.ExportDialogs.ExportDialog;
import idir.embag.Ui.Components.ExportDialogs.ImportDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SettingsPanel extends INodeView implements Initializable {

  @FXML
  private Pane root ;

  public SettingsPanel(){
    fxmlPath = "/views/SettingsPanel.fxml";
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }


  @Override
  public Node getView() {   

    return root;
  }

  @FXML
  private void exportData(){
    ExportDialog dialog = new ExportDialog();
    Map<EEventDataKeys,Object> data = new HashMap<>();
    data.put(EEventDataKeys.DialogContent, dialog);

    StoreCenter storeCenter = StoreCenter.getInstance();
    StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    
    storeCenter.dispatch(event);
  }

  @FXML
  private void importData(){
    ImportDialog dialog = new ImportDialog();
    Map<EEventDataKeys,Object> data = new HashMap<>();
    data.put(EEventDataKeys.DialogContent, dialog);

    StoreCenter storeCenter = StoreCenter.getInstance();
    StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    
    storeCenter.dispatch(event);
  }

}
