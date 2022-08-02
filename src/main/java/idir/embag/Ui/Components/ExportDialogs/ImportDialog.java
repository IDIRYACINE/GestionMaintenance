package idir.embag.Ui.Components.ExportDialogs;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.Generics.MDatabase;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ImportDialog extends INodeView implements Initializable,IDialogContent{

    @FXML
    private VBox root;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private MFXListView<HBox> attributesListView;

    @FXML
    private MFXComboBox<String> tableComboBox;

    @FXML
    private MFXTextField fieldRowStart, fieldRowEnd, fieldColStart, fieldColEnd;

    public ImportDialog() {
        fxmlPath = "/views/ExportDialogs/ImportDialog.fxml";
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys, Object>> callback) {
        
    }

    @Override
    public void setOnCancel(Runnable callback) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableComboBox.getItems().setAll(MDatabase.Tables.All);
        
    }

    @Override
    public Node getView() {
        return root;
    }
    
    @FXML
    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Names.ImportFileTitle);
        fileChooser.showOpenDialog(null);
        
    }

    @FXML
    private void selectTable() {
        
    }

    @FXML
    private void importData() {
        
    }

    @FXML
    private void cancel() {
        
    }

}
