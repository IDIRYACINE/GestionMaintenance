package idir.embag.Ui.Panels.Historique;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Session.SessionRecord;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class HistoryPanel extends INodeView  implements Initializable {
    
    @FXML
    private VBox root;

    @FXML
    private MFXButton btnExport, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<SessionRecord> tableStock;

    

    public HistoryPanel() {
        fxmlPath = "/views/HistoryPanel.fxml";
    }

    @Override
    public Node getView() {
       return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
    

