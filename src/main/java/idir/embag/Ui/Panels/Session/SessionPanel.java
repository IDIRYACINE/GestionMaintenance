package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Workers.SessionRecord;
import idir.embag.Ui.Components.MangerDialog.WorkersManagerDialog;
import idir.embag.Ui.Panels.Generics.INodeView;
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
    private MFXTableView<SessionRecord> tableStock;

   

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
        WorkersManagerDialog fDialog = new WorkersManagerDialog();
        fDialog.loadFxml();
        
       // Dispatch a display event to store
       
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
