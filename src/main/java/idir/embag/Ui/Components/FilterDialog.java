package idir.embag.Ui.Components;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import idir.embag.Infrastructure.Database.AttributeWrapper;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class FilterDialog<T> extends INodeView implements Initializable{

    @FXML
    private VBox root;
    
    @FXML
    private MFXButton btnCancel,btnSearch;

    @FXML
    private MFXListView<T> listViewAttrb;

    @FXML
    private MFXListView<AttributeWrapper<T>> listViewSelectedAttrb;

    private List<T> attributes;

    public FilterDialog(List<T> attributes) {
        fxmlPath = "/views/Forms/FilterDialog.fxml";
        this.attributes = attributes;
    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewAttrb.getItems().addAll(attributes);
    }

    @Override
    public Node getView() {
        return root;
    }

    
}
