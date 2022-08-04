package idir.embag.Ui.Components.Editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionGroup;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionGroupAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class SessionGroupEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField groupNameField;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private SessionGroup group;

    public SessionGroupEditor(SessionGroup group) {
        this.group = group;
        fxmlPath = "/views/Editors/SessionGroupEditor.fxml";

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
        data.put(EEventsDataKeys.Instance, group);
    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        
        attributes.add(new AttributeWrapper(ESessionGroupAttributes.GroupName,groupNameField.getText()));
        attributes.add(new AttributeWrapper(ESessionGroupAttributes.SessionId,String.valueOf(group.getSessionId())));

        return attributes;
    }

    
}

