package idir.embag.Ui.Dialogs.UsersDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.User;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserEditorDialog extends INodeView implements Initializable, IDialogContent {

    @FXML
    private VBox root;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private MFXCheckbox isAdminCheckbox;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys, Object>> confirmTask;

    private User user;

    @FXML
    private MFXListView<HBox> permissionsListView;



    public UserEditorDialog(User user) {
        fxmlPath = "/views/Editors/UserEditor.fxml";
        this.user = user;
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        confirmTask = callback;

    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameField.setText(user.getUserName());
        passwordField.setText(user.getPassword());
        isAdminCheckbox.setSelected(user.isAdmin());

    }

    @FXML
    private void Cancel() {
        cancelTask.run();
    }

    @FXML
    private void Confirm() {
        Map<EEventsDataKeys, Object> data = new HashMap<>();
        
        setupConfirmation(data);
        

        confirmTask.accept(data);
    }




    private void setupConfirmation(Map<EEventsDataKeys, Object> data){

        Collection<AttributeWrapper> attribs = new ArrayList<>();

        String username = usernameField.getText();
        if(!username.equals(user.getUserName())){
            attribs.add(new AttributeWrapper(EUsersAttributes.UserName, username));
            user.setUserName(username);
        }

        String password = passwordField.getText();
        if(!password.equals(user.getPassword())){
            attribs.add(new AttributeWrapper(EUsersAttributes.Password, password));
            user.setPassword(fxmlPath);
        }

        boolean isAdmin = isAdminCheckbox.isSelected();
        if(isAdmin != user.isAdmin()){
            attribs.add(new AttributeWrapper(EUsersAttributes.Admin, isAdmin));
            user.setAdmin(isAdmin);
        }


        // TODO attribute wrap designations


    }

}
