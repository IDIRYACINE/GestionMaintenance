package idir.embag.Ui.Dialogs.UsersDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Affectation;
import idir.embag.DataModels.Users.AffectationPermission;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Models.Users.RequestsData.UpdateUser;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EUsersAttributes;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Dialogs.UsersDialog.Components.AttributeSelector;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserEditorDialog extends INodeView implements Initializable, IDialogContent {

    @FXML
    private VBox root;

    @FXML
    private MFXTextField usernameField;

    @FXML
    private MFXTextField passwordField;

    @FXML
    private MFXCheckbox isAdminCheckbox;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys, Object>> confirmTask;

    private User user;

    @FXML
    private MFXListView<HBox> permissionsListView;

    // use this to keep track of the permissions that ungranted from the granted
    // ones , important to cancel changes
    private ArrayList<HBox> revokedPermissions;

    // use this to keep track of the permissions that were already granted to user
    private ArrayList<HBox> grantedOnLoadPermissions;

    private ArrayList<HBox> newlyGrantedPermissions;

    private ArrayList<Affectation> unGrantedDesignations;

    public UserEditorDialog(User user, ArrayList<Affectation> unGrantedDesignations) {
        fxmlPath = "/views/Editors/UserEditor.fxml";
        this.user = user;
        

        revokedPermissions = new ArrayList<>();
        grantedOnLoadPermissions = new ArrayList<>();
        newlyGrantedPermissions = new ArrayList<>();

        this.unGrantedDesignations = unGrantedDesignations;

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

        ArrayList<HBox> alreadyGranted = createSelectorNodes(user.getDesignations(),true);

        grantedOnLoadPermissions.addAll(alreadyGranted);

        ArrayList<HBox> allPermissions = createSelectorNodes(unGrantedDesignations,false);
        allPermissions.addAll(alreadyGranted);

        permissionsListView.getItems().setAll(allPermissions);

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
        
        cancelTask.run();

    }

    private void setupConfirmation(Map<EEventsDataKeys, Object> data) {

        Collection<AttributeWrapper> fields = new ArrayList<>();

        String username = usernameField.getText();
        if (!username.equals(user.getUserName())) {
            fields.add(new AttributeWrapper(EUsersAttributes.UserName, username));
            user.setUserName(username);
        }

        String password = passwordField.getText();
        if (!password.equals(user.getPassword())) {
            fields.add(new AttributeWrapper(EUsersAttributes.Password, password));
            user.setPassword(password);
        }

        boolean isAdmin = isAdminCheckbox.isSelected();
        if (isAdmin != user.isAdmin()) {
            fields.add(new AttributeWrapper(EUsersAttributes.Admin, isAdmin));
            user.setAdmin(isAdmin);
        }

        Collection<AffectationPermission> grantedP = new ArrayList<>();

        newlyGrantedPermissions.forEach(node -> {

            Affectation designation = (Affectation) node.getUserData();

            grantedP.add(new AffectationPermission(user.getUserId(), designation.getAffectationId()));

            if (!user.getDesignations().contains(designation)) {
                user.getDesignations().add(designation);
            }
        });

        Collection<AffectationPermission> ungrantedP = new ArrayList<>();
        revokedPermissions.forEach(node -> {
            Affectation designation = (Affectation) node.getUserData();

            ungrantedP.add(new AffectationPermission(user.getUserId(), designation.getAffectationId()));

            if (user.getDesignations().contains(designation)) {
                user.getDesignations().remove(designation);
            }
        });

        UpdateUser updateUser = new UpdateUser(fields, grantedP, ungrantedP);

        DataBundler.appendData(data, EEventsDataKeys.RequestData, updateUser);

    }

    private ArrayList<HBox> createSelectorNodes(ArrayList<Affectation> designations, boolean selected) {
        FXMLLoader loader;
        AttributeSelector controller;

        ArrayList<HBox> attributeSelectorNodes = new ArrayList<>();

        for (int i = 0; i < designations.size(); i++) {
            try {
                loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeSelectorCell.fxml"));
                controller = new AttributeSelector(designations.get(i));
                controller.setOnSelect(this::selectAtrribute);
                controller.setOnDeselect(this::deselectAttribute);
                controller.setSelectedInitially(selected);
                loader.setController(controller);
                HBox node = loader.load();
                node.setUserData(designations.get(i));
                attributeSelectorNodes.add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return attributeSelectorNodes;

    }

    private void selectAtrribute(HBox node) {
        if (revokedPermissions.contains(node)) {
            revokedPermissions.remove(node);
        }
        newlyGrantedPermissions.add(node);
    }

    private void deselectAttribute(HBox node) {
        if (grantedOnLoadPermissions.contains(node)) {
            revokedPermissions.add(node);
        }
        newlyGrantedPermissions.remove(node);
    }

}
