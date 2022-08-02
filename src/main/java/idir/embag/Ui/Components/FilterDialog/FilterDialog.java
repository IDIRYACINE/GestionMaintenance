package idir.embag.Ui.Components.FilterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
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

    private Runnable cancelTask;
    private Consumer<Map<EEventDataKeys,Object>> confirmTask;

    public FilterDialog() {
        fxmlPath = "/views/FilterDialog/FilterDialog.fxml";
    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAttributesSelector();
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onConfirm(){
        Map<EEventDataKeys,Object> data = new HashMap<>();
        setupConfirmAction(data);
        confirmTask.accept(data);
        cancelTask.run();
    }

    @FXML
    private void onCancel(){
        cancelTask.run();
    }


    private void setupAttributesSelector(){
        FXMLLoader loader;     
        AttributeSelector controller ;
        
        HBox[] attributeSelectorNodes = new HBox[attributes.length];

        for(int i = 0 ; i < attributes.length;i++){
            try {
                loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeSelectorCell.fxml"));
                controller = new AttributeSelector(attributes[i]);
                controller.setOnSelect(this::selectAtrribute);
                controller.setOnDeselect(this::deselectAttribute);
                loader.setController(controller);
                attributeSelectorNodes[i] = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listViewAttrb.getItems().setAll(attributeSelectorNodes);
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


    private void selectAtrribute(HBox node){
        listViewSelectedAttrb.getItems().add(node);
    }

    private void deselectAttribute(HBox node){
        listViewSelectedAttrb.getItems().remove(node);
    }

    private void setupConfirmAction(Map<EEventDataKeys,Object> data ){

        Collection<AttributeWrapper> searchParameters = new ArrayList<>();

        listViewSelectedAttrb.getItems().forEach(node -> {
            AttributeField controller = (AttributeField) node.getProperties().get("controller");
            searchParameters.add(controller.getAttributeWrapper());
        });

        SearchWrapper searchWrapper = new SearchWrapper(searchParameters);

        data.put(EEventDataKeys.SearchWrapper,searchWrapper);
    }
    
}
