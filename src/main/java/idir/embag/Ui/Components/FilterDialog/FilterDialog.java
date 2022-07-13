package idir.embag.Ui.Components.FilterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterDialog<T> extends INodeView implements Initializable{

    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnCancel,btnConfirm;

    @FXML
    private MFXListView<HBox> listViewAttrb;

    @FXML
    private MFXListView<HBox> listViewSelectedAttrb;

    private List<T> attributes;

    private ArrayList<AttributeField<T>> attributesFieldControllers;
    private ArrayList<HBox> selectedAttributesNodes;

    private ArrayList<AttributeSelector<T>> attributeSelectorControllers;
    private ArrayList<HBox> attributeSelectorNodes;

    public FilterDialog(List<T> attributes) {
        fxmlPath = "/views/FilterDialog/FilterDialog.fxml";
        this.attributes = attributes;
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
    private void onConfirm(){}

    @FXML
    private void onCancel(){}

    private void setupSelectedAttributes(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeWrapperCell.fxml"));     
        AttributeField<T> controller ;

        for(int i = 0 ; i < attributes.size();i++){
            try {
                controller = new AttributeField<T>(attributes.get(i));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeSelectorCell.fxml"));     
        AttributeSelector<T> controller ;

        for(int i = 0 ; i < attributes.size();i++){
            try {
                controller = new AttributeSelector<T>(attributes.get(i));
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
        attributesFieldControllers = new ArrayList<AttributeField<T>>();
        selectedAttributesNodes = new ArrayList<>();

        attributeSelectorControllers = new ArrayList<AttributeSelector<T>>();
        attributeSelectorNodes = new ArrayList<>();
    }
    
}
