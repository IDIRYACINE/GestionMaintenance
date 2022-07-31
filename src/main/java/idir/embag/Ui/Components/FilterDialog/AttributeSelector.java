package idir.embag.Ui.Components.FilterDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AttributeSelector implements Initializable{

    
    HBox attributeWrapperNode;

    @FXML
    private Label attrbLabel;

    @FXML
    private MFXCheckbox attrbCheckBox;

    private EEventDataKeys attribute;

    private Consumer<HBox> onSelect,onDeselect;

    public AttributeSelector(EEventDataKeys attribute) {
        this.attribute = attribute;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attrbLabel.setText(attribute.toString());
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
            loadAttributeWrapper();
        } else {
            onDeselect.accept(attributeWrapperNode);
        }
    }

    private void loadAttributeWrapper(){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FilterDialog/AttributeWrapperCell.fxml"));
                AttributeWrapper wrapper = new AttributeWrapper(attribute,"");
                AttributeField controller = new AttributeField(wrapper);
                loader.setController(controller);
                attributeWrapperNode = loader.load();
                onSelect.accept(attributeWrapperNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    
}
