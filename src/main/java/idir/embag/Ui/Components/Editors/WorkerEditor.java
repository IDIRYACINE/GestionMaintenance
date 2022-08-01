package idir.embag.Ui.Components.Editors;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Workers.Worker;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class WorkerEditor extends INodeView implements Initializable , IDialogContent {

    
    @FXML
    private Node root;

    @FXML
    private TextField workerNameField,workerPhoneField,workerEmailField;

    private Runnable cancelTask;

    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    private Worker worker;

    public WorkerEditor(Worker worker) {
        this.worker = worker;
        fxmlPath = "/views/Editors/WorkerEditor.fxml";

    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys, Object>> callback) {
        this.confirmTask = callback;
    }

    @Override
    public void setOnCancel(Runnable callback) {
        this.cancelTask = callback;
    }

    @Override
    public void setEventKey(EEventDataKeys key) {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        workerEmailField.setText(worker.getEmail());
        workerNameField.setText(worker.getName());
        workerPhoneField.setText(String.valueOf(worker.getPhone()));
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm(){
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        setupConfirm(data);

        confirmTask.accept(data);
        cancelTask.run();
    }
    
    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    private void setupConfirm(Map<EEventDataKeys,Object> data){
        worker.setEmail(workerEmailField.getText());
        worker.setName(workerNameField.getText());
        worker.setPhone(Integer.parseInt(workerPhoneField.getText()));

        data.put(EEventDataKeys.AttributeWrappersList,getAttributeWrappers());
        data.put(EEventDataKeys.WorkerId, worker.getId());
    }

    private Collection<AttributeWrapper> getAttributeWrappers(){
        Collection<AttributeWrapper> attributes = new ArrayList<AttributeWrapper>();
        
        attributes.add(new AttributeWrapper(EEventDataKeys.WorkerName,workerNameField.getText()));
        attributes.add(new AttributeWrapper(EEventDataKeys.WorkerEmail,workerEmailField.getText()));
        attributes.add(new AttributeWrapper(EEventDataKeys.WorkerPhone,workerPhoneField.getText()));

        return attributes;
    }

    
}
