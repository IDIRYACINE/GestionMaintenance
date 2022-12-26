package idir.embag.Ui.Dialogs.MangerDialog;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Controllers.Session.SessionGroupHelper;
import idir.embag.Application.Controllers.Session.SessionWorkersHelper;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionWorkersDialog extends INodeView implements Initializable,IDialogContent {
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXTableView<SessionWorker> tableSessionWorkers;

    @FXML
    private MFXTableView<SessionGroup> tableSessionGroups;


    private SessionWorkersHelper workersController;

    private SessionGroupHelper groupsController;

    public SessionWorkersDialog() {
        fxmlPath = "/views/ManagerDialog/WorkersManagerDialog.fxml";
        workersController = new SessionWorkersHelper();
        groupsController = new SessionGroupHelper();
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupsController.notifyActive(tableSessionGroups);
        workersController.notifyActive(tableSessionWorkers,tableSessionGroups);
    
    }

    @FXML
    private void onRemoveGroup(){
        SessionGroup group = tableSessionGroups.getSelectionModel().getSelectedValues().get(0);
        groupsController.delete(group);
    }

    @FXML
    private void onUpdateGroup(){
        SessionGroup group = tableSessionGroups.getSelectionModel().getSelectedValues().get(0);
        groupsController.update(group);
    }

    @FXML
    private void onAddGroup(){
        groupsController.add();
    }

    @FXML
    private void onRemoveWorker(){
        SessionWorker worker = tableSessionWorkers.getSelectionModel().getSelectedValues().get(0);
        workersController.delete(worker);
    }

    @FXML
    private void onUpdateWorker(){
        SessionWorker worker = tableSessionWorkers.getSelectionModel().getSelectedValues().get(0);
        workersController.update(worker);
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys,Object>> callback) {

    }

    @Override
    public void setOnCancel(Runnable callback) {

    }

}
