package idir.embag.Ui.Panels.Settings;


import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Application.Settings.SettingsController;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SettingsPanel extends INodeView implements Initializable {

  @FXML
  private Pane root ;

  private SettingsController controller;

  public SettingsPanel(){
    fxmlPath = "/views/SettingsPanel.fxml";
    controller = new SettingsController();
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
    controller.exportData();

  }

  @FXML
  private void importData(){
    controller.importData();
  }
  

}
