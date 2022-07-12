package idir.embag.Ui.Views.Historique;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Workers.SessionRecord;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class HistoriqueView implements IHistoriqueView, Initializable {
    
    @FXML
    private VBox root;

    @FXML
    private MFXButton btnExport, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<SessionRecord> tableStock;

    @Override
    public Node getView() {
       return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void loadFxml() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HistoryPanel.fxml"));   
           
            loader.setController(this);  
            loader.load();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

}
    

