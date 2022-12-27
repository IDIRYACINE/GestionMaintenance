package idir.embag.Ui.Dialogs.ExportDialogs;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import idir.embag.Application.Controllers.Exporter.Exporter;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.Types.Infrastructure.DataConverters.ImportWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.DoingWorkDialog;
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

public class ImportDialog extends INodeView implements Initializable, IDialogContent {

    @FXML
    private VBox root;

    @FXML
    private Label selectedFileLabel;

    @FXML
    private MFXListView<HBox> attributesListView;

    @FXML
    private MFXComboBox<EStoreEvents> tableComboBox;

    @FXML
    private MFXTextField rowStartField, rowEndField, colStartField, colEndField;

    private File file;

    private Exporter controller;

    private Runnable cancelCallback;

  

    public ImportDialog() {
        fxmlPath = "/views/ExportDialog/ImportDialog.fxml";
        controller = new Exporter();
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {

    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelCallback = callback;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EStoreEvents[] tables = {
                EStoreEvents.StockEvent,
                EStoreEvents.InventoryEvent,
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
        fileChooser.setTitle(Names.ImportFileTitle);
        file = fileChooser.showOpenDialog(null);
        selectedFileLabel.setText(file.getName());

    }

    @FXML
    private void selectTable() {

    }

    @FXML
    private void importData() {
        EStoreEvents tableType = tableComboBox.getSelectionModel().getSelectedItem();

        ImportWrapper importWrapper = new ImportWrapper(100, tableType);

        importWrapper.setSheetBounds(Integer.parseInt(rowStartField.getText()),
                Integer.parseInt(rowEndField.getText()),
                Integer.parseInt(colStartField.getText()),
                Integer.parseInt(colEndField.getText()));

        importWrapper.setInputFile(file.getAbsolutePath());

        controller.startImport(tableType, importWrapper);

        switchToWorkingView();

    }

    @FXML
    private void cancel() {
        cancelCallback.run();
    }

    private void switchToWorkingView() {
        DoingWorkDialog doingWorkDialog = new DoingWorkDialog(Messages.pleaseWait);

        doingWorkDialog.setOnCancel(() -> {
            controller.cancel();
            cancelCallback.run();
        });

        doingWorkDialog.loadFxml();

        root.getChildren().setAll(doingWorkDialog.getView());
    }
}
