package idir.embag.Application.Controllers.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Models.Stock.StockModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class StockController implements Initializable{

    private StockModel stockModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        stockModel = new StockModel();
        
    }


    @FXML
    // Inventory , Stock
    private void setStockType(){
        
    }
    
}
