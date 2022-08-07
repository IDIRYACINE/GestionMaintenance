package idir.embag.Ui.Panels.Workers;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Workers.WorkersController;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Application.Workers.IWorkersController;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class WorkersPanel extends INodeView implements Initializable{
    
    @FXML
    private VBox root;
    
    @FXML
    private MFXTableView<Worker> tableWorkers;

    IWorkersController controller;

    public WorkersPanel() {
        fxmlPath = "/views/Panels/WorkersPanel.fxml";
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
    private void addWorker(){
        controller.add();
    }


    @FXML
    private void updateWorker(){
        Worker worker = tableWorkers.getSelectionModel().getSelectedValues().get(0);
        controller.update(worker);
    }


    @FXML
    private void archiveWorker(){
        Worker worker = tableWorkers.getSelectionModel().getSelectedValues().get(0);
        controller.archive(worker);
    }

    @FXML
    private void searchWorkers(){
        controller.searchWorkers();
    }

    @FXML
    private void refresh(){
        controller.refresh();
    }

    @FXML
    private void addWorkerToSession(){
        Worker worker = tableWorkers.getSelectionModel().getSelectedValues().get(0);
        controller.addWorkerToSession(worker);
    }

}


    

