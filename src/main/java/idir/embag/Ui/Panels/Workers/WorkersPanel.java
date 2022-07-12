package idir.embag.Ui.Panels.Workers;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Workers.Worker;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class WorkersPanel extends INodeView implements Initializable{
    
    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<Worker> tableStock;

    

    public WorkersPanel() {
        fxmlPath = "/views/WorkersPanel.fxml";
    }

   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }

    @Override
    public Node getView() {
        return root;
    }

}


    

