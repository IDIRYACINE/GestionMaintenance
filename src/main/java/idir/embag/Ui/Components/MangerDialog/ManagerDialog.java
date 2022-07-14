package idir.embag.Ui.Components.MangerDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import idir.embag.Ui.Components.FilterDialog.AttributeField;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ManagerDialog<T> extends INodeView implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private MFXButton btnCancel,btnConfirm;

    @FXML
    private VBox fieldsContainer;

    private T[] attributes;

    private ArrayList<AttributeField<T>> attributesFieldControllers;

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
        
    }

    @FXML
    private void onCancel(){

    }

    public void setOnConfirmText(String title){
        btnConfirm.setText(title);
    }

    public void setAttributes(T[] attributes){
        this.attributes = attributes;
    }

    public void setOnConfirmCallback(Callback<String,Void> callback){

    }

    private void setupFieldRows(){          
        attributesFieldControllers = new ArrayList<>();
        ArrayList<Node> nodes  = new ArrayList<>();
        AttributeField<T> controller ;

        for(int i = 0 ; i < attributes.length;i++){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerDialog/AttributeRow.fxml"));  
                controller = new AttributeField<T>(attributes[i]);
                attributesFieldControllers.add(controller);
                loader.setController(controller);
                nodes.add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fieldsContainer.getChildren().addAll(nodes);

    }


    
}
