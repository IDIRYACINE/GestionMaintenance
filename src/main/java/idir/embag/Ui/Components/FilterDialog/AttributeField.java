package idir.embag.Ui.Components.FilterDialog;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttributeField<T> implements Initializable {

    @FXML
    private HBox root;
    
    @FXML 
    private Label attrbLabel;

    @FXML 
    private MFXTextField attrbValue;

    private AttributeWrapper<T> attributeWrapper;


    public AttributeField(T attribute) {
        this.attributeWrapper = new AttributeWrapper<T>(attribute,"");
    }


    public AttributeWrapper<T> getAttributeWrapper(){
        attributeWrapper.setValue(attrbValue.getText());
        return attributeWrapper;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbValue.setText(attributeWrapper.getValue().toString());
        attrbLabel.setText(attributeWrapper.getAttributeName().toString());
    }



}
    

