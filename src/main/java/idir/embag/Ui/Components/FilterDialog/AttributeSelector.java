package idir.embag.Ui.Components.FilterDialog;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AttributeSelector implements Initializable{

    @FXML
    private Label attrbLabel;

    @FXML
    private MFXCheckbox attrbCheckBox;

    private EEventDataKeys attribute;

    public AttributeSelector(EEventDataKeys attribute) {
        this.attribute = attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbLabel.setText(attribute.toString());
    }


    
}
