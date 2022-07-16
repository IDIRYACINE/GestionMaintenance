package idir.embag.Ui.Components.FilterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
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

    private EEventDataKeys[] attributes;

    private Map<EEventDataKeys,AttributeWrapper> attributesWrappers;
    private Map<EEventDataKeys,HBox> selectedAttributesNodes;
    private Map<EEventDataKeys,HBox> unselectedAttributesNodes; 

    private Runnable cancelTask;
    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    public FilterDialog() {
        fxmlPath = "/views/FilterDialog/FilterDialog.fxml";
    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setup();
        setupAttributesSelector();
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


    private void setupAttributesSelector(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerDialog/AttributeRow.fxml"));     
        AttributeSelector controller ;
        
        HBox[] attributeSelectorNodes = new HBox[attributes.length];

        for(int i = 0 ; i < attributes.length;i++){
            try {
                controller = new AttributeSelector(attributes[i]);
                loader.setController(controller);
                attributeSelectorNodes[i] = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listViewAttrb.getItems().setAll(attributeSelectorNodes);
    }

    private void setup(){
        attributesWrappers = new HashMap<>();      
        selectedAttributesNodes = new HashMap<>();
        unselectedAttributesNodes = new HashMap<>();
    }

    public void setAttributes(EEventDataKeys[] attributes){
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
    }
    
}
