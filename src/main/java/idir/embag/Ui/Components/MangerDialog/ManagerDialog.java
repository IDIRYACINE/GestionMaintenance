package idir.embag.Ui.Components.MangerDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Components.FilterDialog.AttributeField;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ManagerDialog extends INodeView implements Initializable , IDialogContent {

    @FXML
    private BorderPane root;

    @FXML
    private MFXButton btnCancel,btnConfirm;

    @FXML
    private VBox fieldsContainer;

    private String[] attributes;

    private ArrayList<AttributeField> attributesFieldControllers;

    private Runnable cancelTask;
    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    public ManagerDialog() {
        fxmlPath = "/views/ManagerDialog/ManagerDialog.fxml";
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFieldRows();
    }


    @Override
    public Node getView() {
        return root;
    }  

    @FXML
    private void onConfirm(){
        //TODO: get attributes from fields
        Map<EEventDataKeys,Object> data = new HashMap<>();
        confirmTask.accept(data);
        cancelTask.run();
    }

    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    public void setOnConfirmText(String title){
        btnConfirm.setText(title);
    }

    public void setAttributes(String[] attributes){
        this.attributes = attributes;
    }

    private void setupFieldRows(){          
        attributesFieldControllers = new ArrayList<>();
        ArrayList<Node> nodes  = new ArrayList<>();
        AttributeField controller ;

        for(int i = 0 ; i < attributes.length;i++){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerDialog/AttributeRow.fxml"));  
                controller = new AttributeField(attributes[i]);
                attributesFieldControllers.add(controller);
                loader.setController(controller);
                nodes.add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fieldsContainer.getChildren().addAll(nodes);

    }


    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys,Object>> callback) {
        confirmTask = callback;
    }


    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }

    private AttributeWrapper[] getAttributeWrappers(){
        AttributeWrapper[] wrappers = new AttributeWrapper[attributesFieldControllers.size()];
        for(int i = 0 ; i < attributesFieldControllers.size() ; i++){
            wrappers[i] = attributesFieldControllers.get(i).getAttributeWrapper();
        }
        return wrappers;
    }


    
}
