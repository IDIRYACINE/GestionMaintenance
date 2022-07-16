package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Session.SessionController;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Components.MangerDialog.SessionWorkersDialog;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SessionPanel extends INodeView implements Initializable {
    
    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh,btnSearch;
    
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


    @FXML
    private void manageWorkers(){
       
        // Build Dialog content and set it up
        SessionWorkersDialog fDialog = new SessionWorkersDialog();
        fDialog.loadFxml();
        
       // Dispatch a display event to store
       
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new SessionController(tableSession);
        controller.notifyActive();
    }

    
}
