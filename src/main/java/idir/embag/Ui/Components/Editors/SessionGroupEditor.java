package idir.embag.Ui.Components.Editors;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.DataModels.Users.Designation;
import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.EventStore.Models.Permissions.RequestsData.UpdateGroup;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Dialogs.UsersDialog.Components.AttributeSelector;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SessionGroupEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField groupNameField;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private SessionGroup group;

    @FXML
    private MFXListView<HBox> permissionsListView;

    // use this to keep track of the permissions that ungranted from the granted ones , important to cancel changes
    private ArrayList<HBox> revokedPermissions;

    // use this to keep track of the permissions that were already granted to group
    private ArrayList<HBox> grantedOnLoadPermissions;

    private ArrayList<HBox> newlyGrantedPermissions;

    private ArrayList<Designation> ungrantedDesignations;
    
    public SessionGroupEditor(SessionGroup group, ArrayList<Designation> unGranteddesignations) {
        this.group = group;
        fxmlPath = "/views/Editors/SessionGroupEditor.fxml";

        revokedPermissions = new ArrayList<>();
        grantedOnLoadPermissions = new ArrayList<>();
        newlyGrantedPermissions = new ArrayList<>();

        ungrantedDesignations = unGranteddesignations;
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        this.confirmTask = callback;
    }

    @Override
    public void setOnCancel(Runnable callback) {
        this.cancelTask = callback;
    }

  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupNameField.setText(group.getName());

        ArrayList<HBox> alreadyGranted = createSelectorNodes(group.getDesignations(),true);
        
        newlyGrantedPermissions.addAll(alreadyGranted);
        grantedOnLoadPermissions.addAll(alreadyGranted);

        ArrayList<HBox> allPermissions = createSelectorNodes(ungrantedDesignations,false);
        allPermissions.addAll(alreadyGranted);

        permissionsListView.getItems().setAll(allPermissions);
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm(){
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        setupConfirm(data);

        confirmTask.accept(data);
        cancelTask.run();
    }
    
    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    private void setupConfirm(Map<EEventsDataKeys,Object> data){
        group.setName(groupNameField.getText());

        
        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection, getAttributeWrappers());
        
        Collection<AttributeWrapper> fields = new ArrayList<>();

        String groupName = groupNameField.getText();
        if(!groupName.equals(group.getName())){
            fields.add(new AttributeWrapper(ESessionGroupAttributes.GroupName, groupName));
            group.setName(groupName);
        }

        if(group.getName().equals("")){
            fields.add(new AttributeWrapper(ESessionGroupAttributes.GroupSupervisorId, AppState.getInstance().getCurrentUser().getUserId()));
        }

        fields.add(new AttributeWrapper(ESessionGroupAttributes.GroupName, groupNameField.getText()));

        
        data.put(EEventsDataKeys.Instance, group);

        Collection<DesignationPermission> grantedP = new ArrayList<>();

        newlyGrantedPermissions.forEach( node -> {
            Designation designation = (Designation) node.getUserData();
            
            grantedP.add(new DesignationPermission(group.getId(), designation.getDesignationId()));

            if(!group.getDesignations().contains(designation)){
                group.getDesignations().add(designation);
            }
        });
        
        Collection<DesignationPermission> ungrantedP = new ArrayList<>();
        revokedPermissions.forEach(node ->{
            Designation designation = (Designation) node.getUserData();

            ungrantedP.add(new DesignationPermission(group.getId(), designation.getDesignationId()));

            if(group.getDesignations().contains(designation)){
                group.getDesignations().remove(designation);
            }
        });

        UpdateGroup updateGroup = new UpdateGroup(fields, grantedP, ungrantedP );

        data.put(EEventsDataKeys.RequestData, updateGroup);
    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        
        attributes.add(new AttributeWrapper(ESessionGroupAttributes.GroupName,groupNameField.getText()));
        attributes.add(new AttributeWrapper(ESessionGroupAttributes.SessionId,String.valueOf(group.getSessionId())));

        return attributes;
    }



    private ArrayList<HBox> createSelectorNodes(ArrayList<Designation> designations,boolean selected){
        FXMLLoader loader;     
        AttributeSelector controller ;
        
        ArrayList<HBox> attributeSelectorNodes = new ArrayList<>();

        for(int i = 0 ; i < designations.size();i++){
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

