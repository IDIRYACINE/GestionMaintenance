package idir.embag.Ui.Views.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Products.EStockTypes;
import idir.embag.DataModels.Products.IProduct;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class StockView implements IStockView, Initializable {

    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<IProduct> tableStock;

    @FXML
    private MFXComboBox<EStockTypes> comboStockType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        comboStockType.getItems().addAll(EStockTypes.values());
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void loadFxml() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StockPanel.fxml"));   
           
            loader.setController(this);  
            loader.load();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }

}


    

