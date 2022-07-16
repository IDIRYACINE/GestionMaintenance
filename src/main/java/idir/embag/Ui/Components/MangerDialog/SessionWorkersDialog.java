package idir.embag.Ui.Components.MangerDialog;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionWorkersDialog extends INodeView implements Initializable,IDialogContent {
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnUpdateGroup,btnRemoveGroup,btnUpdateWorker,btnRemoveWorker,btnAddGroup;

    @FXML
    private MFXTableView<SessionWorker> tableSessionWorkers;

    @FXML
    private MFXTableView<SessionGroup> tableSessionGroups;

    public SessionWorkersDialog() {
        fxmlPath = "/views/ManagerDialog/WorkersManagerDialog.fxml";
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }

    @FXML
    private void onRemoveGroup(){
    }

    @FXML
    private void onUpdateGroup(){
    }

    @FXML
    private void onAddGroup(){
    }

    @FXML
    private void onRemoveWorker(){
    }

    @FXML
    private void onUpdateWorker(){
    }



    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys,Object>> callback) {
    }

    @Override
    public void setOnCancel(Runnable callback) {
    }

    @Override
    public void setEventKey(EEventDataKeys key) {
    }

  

}
