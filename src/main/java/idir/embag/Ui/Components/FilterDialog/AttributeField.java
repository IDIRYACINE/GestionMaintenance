package idir.embag.Ui.Components.FilterDialog;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Metadata.EEventDataKeys;
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

    private EEventDataKeys attributeKey;

    public AttributeField(String attribute, EEventDataKeys attributeKey) {
        this.attributeWrapper = new AttributeWrapper(attribute, "");
        this.attributeKey = attributeKey;
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

    public EEventDataKeys getAttributeKey() {
        return attributeKey;
    }



}
    

