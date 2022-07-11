package idir.embag.Application.Controllers.Historique;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Models.Historique.IHistoriqueModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HistoriqueController implements Initializable{

    
    private IHistoriqueModel historiqueModel;

    @FXML
    private MFXButton search , refresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    @FXML
    private void search(){}

    @FXML
    private void addSearchField(){}
    
    @FXML
    private void removeSearchField(){}

    @FXML
    private void addSearchResultAttribute(){}

    @FXML
    private void removeSearchResultAttribute(){}
    
}
