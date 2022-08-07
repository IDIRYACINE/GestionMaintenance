package idir.embag.Ui.Dialogs;

import java.net.URL;
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

public class DoingWorkDialog extends INodeView implements Initializable,IDialogContent {
    
    @FXML
    private VBox root;

    @FXML
    private Label messageLabel;

    private Runnable cancelCallback;

    private String message;

    public DoingWorkDialog(String message) {
        fxmlPath = "/views/Forms/DoingWorkDialog.fxml";
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageLabel.setText(message);
    }

    @FXML
    private void onCancel(){
       cancelCallback.run();
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
       
        
    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelCallback = callback;
    }

    

}    