package idir.embag.Ui.Components.FilterDialog;

import java.net.URL;
import java.util.ResourceBundle;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AttributeField implements Initializable {

    @FXML 
    private Label attributeLabel;

    @FXML 
    private MFXTextField attributeField;

    private AttributeWrapper attributeWrapper;

    public AttributeField(AttributeWrapper wrapper) {
        this.attributeWrapper = wrapper;
    }


    public AttributeWrapper getAttributeWrapper(){
        attributeWrapper.setValue(attributeField.getText());
        return attributeWrapper;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attributeField.setText(attributeWrapper.getValue().toString());
        attributeLabel.setText(attributeWrapper.getAttributeName().toString());
    }

    
    

}
    

