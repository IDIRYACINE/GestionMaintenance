package idir.embag.Ui.Panels.Workers;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Workers.IWorkersController;
import idir.embag.Application.Controllers.Workers.WorkersController;
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
    private MFXTableView<Worker> tableWorkers;

    IWorkersController controller;

    public WorkersPanel() {
        fxmlPath = "/views/WorkersPanel.fxml";
    }

   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = new WorkersController(tableWorkers);
        controller.notifyActive();
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void addWorker(){}


    @FXML
    private void updateWorker(){}


    @FXML
    private void archiveWorker(){}


    @FXML
    private void addWorkerToSession(){}

}


    

