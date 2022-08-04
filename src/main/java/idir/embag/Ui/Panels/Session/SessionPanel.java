package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Session.SessionController;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionPanel extends INodeView implements Initializable {
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXTableView<SessionRecord> tableSession;

    private SessionController controller;

    public SessionPanel() {
        fxmlPath = "/views/SessionPanel.fxml";
        
    }

    @Override
    public Node getView() {
        return root;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new SessionController(tableSession);
    }

    @FXML
    private void closeSession(){
        controller.closeSession();
    }

    @FXML
    private void refresh(){
        controller.refresh();
    }

    @FXML
    private void export(){
        controller.export();
    }

    @FXML
    private void manageWorkers(){
        controller.manageSessionGroups();
    }


    
}
