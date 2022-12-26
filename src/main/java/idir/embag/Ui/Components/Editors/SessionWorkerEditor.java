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
import idir.embag.DataModels.Workers.SessionWorker;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.ESessionWorkerAttributes;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class SessionWorkerEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField usernameField,passwordField;

    @FXML
    private MFXComboBox<SessionGroup> groupComboBox;

    private Runnable cancelTask;

    private Consumer<Map<EEventsDataKeys,Object>> confirmTask;

    private SessionWorker worker;

    private Collection<SessionGroup> groups;

    public SessionWorkerEditor(SessionWorker worker,Collection<SessionGroup> groups) {
        this.worker = worker;
        fxmlPath = "/views/Editors/SessionWorkerEditor.fxml";
        this.groups = groups;

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
        passwordField.setText(worker.getPassword());
        usernameField.setText(worker.getUsername());

        groupComboBox.getItems().addAll(groups);
        setCurrentWorkerGroupAsSelected();

        groupComboBox.setConverter(new StringConverter<SessionGroup>() {
            @Override
            public String toString(SessionGroup object) {
                if (object == null) {
                    return "";
                }
                return object.getName();
            }

            @Override
            public SessionGroup fromString(String string) {
                return null;
            }
        });
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
        worker.setPassword(passwordField.getText());

        DataBundler.bundleNestedData(data, EEventsDataKeys.WrappersKeys, EWrappers.AttributesCollection, getAttributeWrappers());
        data.put(EEventsDataKeys.Instance, worker);
    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        String username = usernameField.getText();
        String password = passwordField.getText();


        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();

        if(!username.equals(worker.getUsername()))
            attributes.add(new AttributeWrapper(ESessionWorkerAttributes.Username,worker.getUsername()));
        
        if(!password.equals(worker.getPassword()))
            attributes.add(new AttributeWrapper(ESessionWorkerAttributes.Password,worker.getPassword()));
      
        return attributes;
    }


    private void setCurrentWorkerGroupAsSelected(){
        for (SessionGroup group : groups) {
            if(group.getId() == worker.getGroupId()){
                groupComboBox.getSelectionModel().selectItem(group);
                break;
            }
        }
    }
    
}

