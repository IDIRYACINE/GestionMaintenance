package idir.embag.Ui.Panels.Login;

import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Application.Controllers.Login.LoginController;
import idir.embag.Application.Utility.Validator.Validators;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Components.TextFieldSkins.CustomFieldSkin;
import idir.embag.Ui.Components.TextFieldSkins.SkinErrorTester;
import idir.embag.Ui.Constants.Messages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPanel extends INodeView implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordErrorLabel;

    private CustomFieldSkin usernameSkin,passwordSkin;


    LoginController controller;

    public LoginPanel() {
        fxmlPath = "/views/Panels/HistoryPanel.fxml";
        controller = new LoginController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTextFieldsValidation();
    }

    private void setupTextFieldsValidation() {
        SkinErrorTester emptyFieldTester = new SkinErrorTester(Messages.errorRequiredField,Validators::emptyField);
        SkinErrorTester invalidName = new SkinErrorTester(Messages.errorInvalidName, Validators::isName);

        usernameSkin = new CustomFieldSkin(usernameField, usernameErrorLabel);
        usernameSkin.addErrorTester(emptyFieldTester);
        usernameSkin.addErrorTester(invalidName);
        usernameField.setSkin(usernameSkin);

        passwordSkin = new CustomFieldSkin(passwordField, passwordErrorLabel);
        passwordSkin.addErrorTester(emptyFieldTester);
        passwordField.setSkin(passwordSkin);
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!usernameSkin.checkError() && !passwordSkin.checkError())
            controller.login(username, password);

    }

}
