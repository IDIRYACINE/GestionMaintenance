package idir.embag.Ui.Panels.Historique;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.History.HistoryController;
import idir.embag.Application.Controllers.History.SessionHelper;
import idir.embag.Application.Controllers.History.SessionRecordHelper;
import idir.embag.DataModels.Metadata.EHistoryTypes;
import idir.embag.Types.Application.History.IHistoryController;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
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
    private MFXScrollPane tableHolder;

    private IHistoryController controller;

    public HistoryPanel() {
        fxmlPath = "/views/Panels/HistoryPanel.fxml";
    }

    @Override
    public Node getView() {
       return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessionRecordHelper recordHelper = new SessionRecordHelper();
        SessionHelper sessionHelper = new SessionHelper();

        controller = new HistoryController(sessionHelper,recordHelper);
        controller.selectHistoryHelper(EHistoryTypes.SessionRecord);

        comboHistoryType.getItems().addAll(EHistoryTypes.values());
        comboHistoryType.selectLast();
    }

    @FXML
    private void refresh(){
        controller.refresh();
    }

    @FXML
    private void export(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @FXML
    private void search(){
        System.out.println("search");
        controller.search();
    }

    @FXML
    private void onHistoryTypeChanged(){
        EHistoryTypes HistoryType = comboHistoryType.getSelectionModel().getSelectedItem();
        controller.selectHistoryHelper(HistoryType);
        setTableView();
    }

    private void setTableView(){
        tableHolder.setContent(controller.getTableView());
    }
}
    

