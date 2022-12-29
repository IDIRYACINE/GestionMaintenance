package idir.embag.Ui.Dialogs.UsersDialog.Components;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Users.Designation;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttributeSelector  implements Initializable{

    
    HBox attributeWrapperNode;

    @FXML
    private Label attrbLabel;

    @FXML
    private MFXCheckbox attrbCheckBox;

    private Designation attribute;

    private Consumer<HBox> onSelect,onDeselect;

    public AttributeSelector(Designation attributes) {
        this.attribute = attributes;
    }

    public Designation getDesignation() {
        return attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbLabel.setText(attribute.getDesignationName());
        attrbCheckBox.setOnAction(event -> {
            handleCheckBoxToggling();
        });
        
    }

    public void setOnSelect(Consumer<HBox> onSelect) {
        this.onSelect = onSelect;
    }

    public void setOnDeselect(Consumer<HBox> onDeselect) {
      this.onDeselect = onDeselect;  
    }

    private void handleCheckBoxToggling() {
        if (attrbCheckBox.isSelected()) {
            onSelect.accept(attributeWrapperNode);

        } else {
            onDeselect.accept(attributeWrapperNode);
        }
    }
    
    
}
