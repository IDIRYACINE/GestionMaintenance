package idir.embag.Ui.Components.FilterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterDialog extends INodeView implements Initializable , IDialogContent{

    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnCancel,btnConfirm;

    @FXML
    private MFXListView<HBox> listViewAttrb;

    @FXML
    private MFXListView<HBox> listViewSelectedAttrb;

    private String[] attributes;

    private ArrayList<AttributeField> attributesFieldControllers;
    private ArrayList<HBox> selectedAttributesNodes;

    private ArrayList<AttributeSelector> attributeSelectorControllers;
    private ArrayList<HBox> attributeSelectorNodes;

    private Runnable cancelTask;
    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    private EEventDataKeys key = EEventDataKeys.None;

    public FilterDialog() {
        fxmlPath = "/views/FilterDialog/FilterDialog.fxml";
    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setup();
        setupAttributesSelector();
        setupSelectedAttributes();
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm(){
        //TODO: fix this
        Map<EEventDataKeys,Object> data = new HashMap<>();
        confirmTask.accept(data);
        cancelTask.run();
    }

    @FXML
    private void onCancel(){
        cancelTask.run();
    }

    private void setupSelectedAttributes(){
        AttributeField controller ;

        for(int i = 0 ; i < attributes.length;i++){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeWrapperCell.fxml"));    
                controller = new AttributeField(attributes[i],EEventDataKeys.None);
                attributesFieldControllers.add(controller);
                loader.setController(controller);
                selectedAttributesNodes.add(loader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listViewSelectedAttrb.getItems().addAll(selectedAttributesNodes);

    }

    private void setupAttributesSelector(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerDialog/AttributeRow.fxml"));     
        AttributeSelector controller ;

        for(int i = 0 ; i < attributes.length;i++){
            try {
                controller = new AttributeSelector(attributes[i]);
                attributeSelectorControllers.add(controller);
                loader.setController(controller);
                attributeSelectorNodes.add(loader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listViewAttrb.getItems().addAll(attributeSelectorNodes);

    }

    private void setup(){
        attributesFieldControllers = new ArrayList<AttributeField>();
        selectedAttributesNodes = new ArrayList<>();

        attributeSelectorControllers = new ArrayList<AttributeSelector>();
        attributeSelectorNodes = new ArrayList<>();
    }

    public void setAttributes(String[] attributes){
        this.attributes = attributes;
    }


    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys,Object>> callback) {
        confirmTask = callback;
    }


    @Override
    public void setOnCancel(Runnable callback) {
        cancelTask = callback;
    }


    @Override
    public void setEventKey(EEventDataKeys key) {
        this.key = key;
    }
    
}
