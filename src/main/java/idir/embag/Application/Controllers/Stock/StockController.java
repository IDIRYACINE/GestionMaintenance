package idir.embag.Application.Controllers.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Products.EStockTypes;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class StockController implements Initializable{



    @FXML
    private MFXComboBox<EStockTypes> selectedStockType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
      
        
    }

    /**Inventory , Stock */
    @FXML
    private void setStockType(){
      
    }

    @FXML
    private void search(){}

    @FXML
    private void addSearchResultAttribute(){}
    
    @FXML
    private void removeSearchResultAttribute(){}

    @FXML
    private void addSearchField(){}

    @FXML
    private void removeSearchField(){}

    /** Prompt a dialog to specify the action (add,update,remove) */
    @FXML
    private void manageProduct(){}
}
