package idir.embag.Ui.Panels.Login;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Login.LoginController;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPanel  extends INodeView  implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;

    LoginController controller;

    public LoginPanel(){
        fxmlPath = "/views/Panels/HistoryPanel.fxml";
        controller = new LoginController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void login(){
        String username = usernameField.getText();
        String password = passwordField.getText();

        controller.login(username,password);
        
    }

    

}
