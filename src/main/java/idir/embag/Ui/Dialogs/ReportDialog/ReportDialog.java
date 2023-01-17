package idir.embag.Ui.Dialogs.ReportDialog;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Infrastructure.DataConverters.ExportWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Panels.Generics.INodeView;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.DoingWorkDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ReportDialog extends INodeView implements Initializable,IDialogContent{
    
    @FXML
    private VBox root;

    @FXML
    private Label selectedFileLabel;


    private ReportController controller;

    private File file;

    private Runnable cancelCallback;



    public ReportDialog(ArrayList<SessionRecord> records) {
        fxmlPath = "/views/ExportDialog/ReportDialog.fxml";
        controller = new ReportController(records);
    }

    @Override
    public void setOnConfirm(Consumer<Map<EEventsDataKeys, Object>> callback) {
        
    }

    @Override
    public void setOnCancel(Runnable callback) {
        cancelCallback = callback;
        controller.setDoneCallback(callback);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        
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
        selectedFileLabel.setText(file.getName());
    }

   

    @FXML
    private void exportData() {
        

        LoadWrapper loadWrapper = new LoadWrapper(5000,0);
        ExportWrapper exportWrapper = new ExportWrapper(loadWrapper, EStoreEvents.ReportEvent);

        exportWrapper.setSheetBounds(0,0,0,0);
        exportWrapper.setOutputFile(file.getAbsolutePath());

        controller.startExport(exportWrapper);

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
