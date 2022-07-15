package idir.embag.Ui.Components.MangerDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.ESessionWorker;
import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class WorkersManagerDialog extends INodeView implements Initializable,IDialogContent {
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnUpdate,btnRemove;

    @FXML
    private MFXTableView<ESessionWorker> tableSessionWorkers;

    private Runnable cancelTask;
    private Consumer<Object> confirmTask;

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

        tableSessionWorkers.getTableColumns().setAll(columns);
    }

    @FXML
    private void onRemove(){
        cancelTask.run();
    }

    @FXML
    private void onUpdate(){
        //TODO: properly handle this
        confirmTask.accept("t");
    }

    @Override
    public void setOnConfirm(Consumer<Object> callback) {
        confirmTask = callback;
    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }

  

}
