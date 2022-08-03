package idir.embag.Ui.Components.ExportDialogs;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Exporter.Exporter;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ExportDialog extends INodeView implements Initializable,IDialogContent{
    
    @FXML
    private VBox root;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private MFXListView<HBox> attributesListView;

    @FXML
    private MFXComboBox<EStoreEvents> tableComboBox;

    private Exporter controller;

    private File file;

    public ExportDialog() {
        fxmlPath = "/views/ExportDialog/ExportDialog.fxml";
        controller = new Exporter();
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventDataKeys, Object>> callback) {
        
    }

    @Override
    public void setOnCancel(Runnable callback) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EStoreEvents[] tables = {
            EStoreEvents.SessionEvent,
            EStoreEvents.StockEvent,
            EStoreEvents.InventoryEvent,
            EStoreEvents.SessionRecordsEvent,
            EStoreEvents.FamilyCodeEvent,
            EStoreEvents.WorkersEvent
        };

        tableComboBox.getItems().setAll(tables);

        
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Names.ExportFileTitle);

        file = fileChooser.showSaveDialog(null);
        selectedFileLabel.setText(file.getAbsolutePath());
    }

    @FXML
    private void selectTable() {
        
    }

    @FXML
    private void exportData() {
        EStoreEvents tableType = tableComboBox.getSelectionModel().getSelectedItem();

        LoadWrapper loadWrapper = new LoadWrapper(100,0);
        ExportWrapper exportWrapper = new ExportWrapper(loadWrapper, tableType);

        exportWrapper.setSheetBounds(0,0,0,0);
        exportWrapper.setOutputFile(file.getAbsolutePath());

        controller.startExport(tableType,exportWrapper);
    }

    @FXML
    private void cancel() {
        
    }
    
}
