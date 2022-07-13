package idir.embag.Ui.Components.MangerDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import idir.embag.DataModels.Workers.ESessionWorker;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class WorkersManagerDialog extends INodeView implements Initializable{
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnUpdate,btnRemove;

    @FXML
    private MFXTableView<ESessionWorker> tableSessionWorkers;

    

    public WorkersManagerDialog() {
        fxmlPath = "/views/ManagerDialog/WorkersManagerDialog.fxml";
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
        setupColumns();
    }

    private void setupColumns(){

        ESessionWorker[] rawColumns = ESessionWorker.values();
        ArrayList<MFXTableColumn<ESessionWorker>> columns = new ArrayList<MFXTableColumn<ESessionWorker>>();

        for(int i = 0 ; i < rawColumns.length ; i++){
           columns.add(new MFXTableColumn<ESessionWorker>(rawColumns[i].toString()));
        }

        tableSessionWorkers.getTableColumns().addAll(columns);
    }

    @FXML
    private void onRemove(){}

    @FXML
    private void onUpdate(){}
    
}
