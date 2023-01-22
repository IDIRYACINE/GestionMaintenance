package idir.embag.Ui.Panels.Session;

import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Application.Controllers.Session.SessionController;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class NoSessionFrame extends INodeView implements Initializable {
    
    @FXML
    private VBox root;


    public NoSessionFrame(SessionController controller) {
        fxmlPath = "/views/Panels/NoSessionPanel.fxml";
        this.controller = controller;
    }

    @Override
    public Node getView() {
        return root;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void openNewSession(){
    }

    @FXML
    private void checkActiveSession(){
    }
    
}
