package idir.embag.Ui.Components.ConfirmationDialog;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ConfirmationDialog extends INodeView implements Initializable , IDialogContent{

    @FXML
    private MFXButton btnCancel,btnConfirm;

    @FXML
    private Label labelMessage;

    @FXML
    private VBox root;

    private Runnable cancelTask;

    private Consumer<Object> confirmTask;
    
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
        confirmTask.accept("Test");
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
    public void setOnConfirm(Consumer<Object> callback) {
        confirmTask = callback;
    }


    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }


    
}
