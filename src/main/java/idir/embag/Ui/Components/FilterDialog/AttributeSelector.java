package idir.embag.Ui.Components.FilterDialog;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AttributeSelector<T> implements Initializable{

    @FXML
    private Label attrbLabel;

    @FXML
    private MFXCheckbox attrbCheckBox;

    private T attribute;

    public AttributeSelector(T attribute) {
        this.attribute = attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbLabel.setText(attribute.toString());
    }


    
}
