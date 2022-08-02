package idir.embag.Ui.Panels.Historique;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.History.HistoryController;
import idir.embag.Application.History.SessionHelper;
import idir.embag.Application.History.SessionRecordHelper;
import idir.embag.DataModels.Metadata.EHistoryTypes;
import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Application.History.IHistoryController;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class HistoryPanel extends INodeView  implements Initializable {
    
    @FXML
    private VBox root;

    @FXML
    private MFXComboBox<EHistoryTypes> comboHistoryType;
    
    @FXML
    private MFXTableView<SessionRecord> tableSessionRecords;

    private IHistoryController controller;

    public HistoryPanel() {
        fxmlPath = "/views/HistoryPanel.fxml";
    }

    @Override
    public Node getView() {
       return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessionRecordHelper recordHelper = new SessionRecordHelper(tableSessionRecords);

        // TODO: Session records and records history table swap
        SessionHelper sessionHelper = new SessionHelper();

        controller = new HistoryController(sessionHelper,recordHelper);

        controller.selectHistoryHelper(EHistoryTypes.SessionRecord);
        comboHistoryType.getItems().addAll(EHistoryTypes.values());
    }

    @FXML
    private void refresh(){
        controller.refresh();
    }

    @FXML
    private void search(){
        controller.search();
    }

    @FXML
    private void onHistoryTypeChanged(){
        EHistoryTypes HistoryType = comboHistoryType.getSelectionModel().getSelectedItem();
        controller.selectHistoryHelper(HistoryType);
    }
}
    

