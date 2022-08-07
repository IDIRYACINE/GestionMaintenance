package idir.embag.Ui.Dialogs.ConfirmationDialog;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ConfirmationDialog extends INodeView implements Initializable , IDialogContent{


    @FXML
    private Label labelMessage;

    @FXML
    private VBox root;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;
    
    private String message;

    public ConfirmationDialog() {
        fxmlPath = "/views/Forms/ConfirmationDialog.fxml";
    }


    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      labelMessage.setText(message);
    }

    @FXML
    private void onConfirm(){
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        confirmTask.accept(data);
        cancelTask.run();
    }

    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    public void setMessage(String message){
       this.message = message;
    }


    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys,Object>> callback) {
        confirmTask = callback;
    }


    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }


}
