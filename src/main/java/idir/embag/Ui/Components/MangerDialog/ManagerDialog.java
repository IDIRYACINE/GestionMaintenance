package idir.embag.Ui.Components.MangerDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Components.FilterDialog.AttributeField;
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

    private EEventDataKeys[] attributes;

    private Map<EEventDataKeys,AttributeWrapper> attributesWrappers;

    private Runnable cancelTask;

    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    private EEventDataKeys key ;

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
        Map<EEventDataKeys,Object> data = new HashMap<>();
        getAttributeWrappers(data);
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

    public void setAttributes(EEventDataKeys[] attributes){
        this.attributes = attributes;
    }

    private void setupFieldRows(){          
        attributesWrappers = new HashMap<>();
        ArrayList<Node> nodes  = new ArrayList<>();
        AttributeField controller ;
        AttributeWrapper wrapper ;

        for(int i = 0 ; i < attributes.length;i++){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerDialog/AttributeRow.fxml"));  
                wrapper = new AttributeWrapper(attributes[i], "");
                controller = new AttributeField(wrapper);
                attributesWrappers.put(attributes[i],wrapper);
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

    private void getAttributeWrappers(Map<EEventDataKeys,Object> data){
        data.put(key, attributesWrappers);
    }


    @Override
    public void setEventKey(EEventDataKeys key) {
        this.key = key;
    }


    
}
