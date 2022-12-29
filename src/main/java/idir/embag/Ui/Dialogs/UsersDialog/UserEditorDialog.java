package idir.embag.Ui.Dialogs.UsersDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Designation;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Models.Users.RequestsData.UpdateUser;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationsPermissions;
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

    // use this to keep track of the permissions that ungranted from the granted ones , important to cancel changes
    private ArrayList<HBox> revokedPermissions;

    // use this to keep track of the permissions that were already granted to user
    private ArrayList<HBox> grantedOnLoadPermissions;

    private ArrayList<HBox> newlyGrantedPermissions;

    private ArrayList<Designation> ungrantedDesignations;

    public UserEditorDialog(User user, ArrayList<Designation> designations) {
        fxmlPath = "/views/Editors/UserEditor.fxml";
        this.user = user;
        
        revokedPermissions = new ArrayList<>();
        grantedOnLoadPermissions = new ArrayList<>();
        newlyGrantedPermissions = new ArrayList<>();

        ungrantedDesignations = designations;


        
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
    
        
        ArrayList<HBox> alreadyGranted = createSelectorNodes(user.getDesignations());
        
        newlyGrantedPermissions.addAll(alreadyGranted);
        grantedOnLoadPermissions.addAll(alreadyGranted);

        ArrayList<HBox> allPermissions = createSelectorNodes(ungrantedDesignations);
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
    }




    private void setupConfirmation(Map<EEventsDataKeys, Object> data){

        Collection<AttributeWrapper> fields = new ArrayList<>();

        String username = usernameField.getText();
        if(!username.equals(user.getUserName())){
            fields.add(new AttributeWrapper(EUsersAttributes.UserName, username));
            user.setUserName(username);
        }

        String password = passwordField.getText();
        if(!password.equals(user.getPassword())){
            fields.add(new AttributeWrapper(EUsersAttributes.Password, password));
            user.setPassword(fxmlPath);
        }

        boolean isAdmin = isAdminCheckbox.isSelected();
        if(isAdmin != user.isAdmin()){
            fields.add(new AttributeWrapper(EUsersAttributes.Admin, isAdmin));
            user.setAdmin(isAdmin);
        }

        Collection<AttributeWrapper> grantedP = new ArrayList<>();

        newlyGrantedPermissions.forEach( node -> {
            Designation designation = (Designation) node.getUserData();
            
            grantedP.add(new AttributeWrapper(EDesignationsPermissions.DesignationId, designation));

            if(!user.getDesignations().contains(designation)){
                user.getDesignations().add(designation);
            }
        });
        
        Collection<AttributeWrapper> ungrantedP = new ArrayList<>();
        revokedPermissions.forEach(node ->{
            Designation designation = (Designation) node.getUserData();

            ungrantedP.add(new AttributeWrapper(EDesignationsPermissions.DesignationId, designation));

            if(user.getDesignations().contains(designation)){
                user.getDesignations().remove(designation);
            }
        });

        UpdateUser updateUser = new UpdateUser(fields, grantedP, ungrantedP );

        data.put(EEventsDataKeys.RequestData, updateUser);

    }


    private ArrayList<HBox> createSelectorNodes(ArrayList<Designation> designations){
        FXMLLoader loader;     
        AttributeSelector controller ;
        
        ArrayList<HBox> attributeSelectorNodes = new ArrayList<>();

        for(int i = 0 ; i < designations.size();i++){
            try {
                loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeSelectorCell.fxml"));
                controller = new AttributeSelector(designations.get(i));
                controller.setOnSelect(this::selectAtrribute);
                controller.setOnDeselect(this::deselectAttribute);
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


    private void selectAtrribute(HBox node){
        if(revokedPermissions.contains(node)){
            revokedPermissions.remove(node);
        }
        newlyGrantedPermissions.add(node);
    }

    private void deselectAttribute(HBox node){
        if(grantedOnLoadPermissions.contains(node)){
            revokedPermissions.add(node);
        }
        newlyGrantedPermissions.remove(node);        
    }




}


