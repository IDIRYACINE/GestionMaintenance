package idir.embag.Ui.Dialogs.UsersDialog.Components;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Users.Affectation;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttributeSelector  implements Initializable{

    @FXML
    HBox root;

    @FXML
    private Label attrbLabel;

    @FXML
    private MFXCheckbox attrbCheckBox;

    private Affectation attribute;

    private Consumer<HBox> onSelect,onDeselect;

    private boolean isSelectedInitially = false;

    public AttributeSelector(Affectation attributes) {
        this.attribute = attributes;
    }

    public Affectation getDesignation() {
        return attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbLabel.setText(attribute.getAffectationName());
        attrbCheckBox.setOnAction(event -> {
            handleCheckBoxToggling();
        });

        attrbCheckBox.setSelected(isSelectedInitially);
        
    }

    public void setOnSelect(Consumer<HBox> onSelect) {
        this.onSelect = onSelect;
    }

    public void setOnDeselect(Consumer<HBox> onDeselect) {
      this.onDeselect = onDeselect;  
    }

    private void handleCheckBoxToggling() {
        if (attrbCheckBox.isSelected()) {
            onSelect.accept(root);

        } else {
            onDeselect.accept(root);
        }
    }

    public void setSelectedInitially(boolean isSelectedInitially) {
        this.isSelectedInitially = isSelectedInitially;
    }

    
    
    
}
