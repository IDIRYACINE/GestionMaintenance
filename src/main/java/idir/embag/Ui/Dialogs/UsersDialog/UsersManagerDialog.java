package idir.embag.Ui.Dialogs.UsersDialog;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.User;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class UsersManagerDialog  extends INodeView implements Initializable, IDialogContent {
   
    private Runnable cancelTask;

    @FXML
    private BorderPane root;

    @FXML
    private MFXTableView<User> usersTable;

    private UsersManagerController controller;

    public UsersManagerDialog() {
        fxmlPath = "/views/UsersDialog/ManageUsersDialog.fxml";
        controller = new UsersManagerController();
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        
    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.notifyActive(usersTable);
    }

    @FXML
    private void Done() {
        cancelTask.run();
    }

    @FXML
    private void AddUser(){
        controller.addUser();
    }

    @FXML
    private void UpdateUser(){
        controller.updateUser();
    }

    @FXML
    private void DeleteUser(){
        controller.deleteUser();
    }

    
}
