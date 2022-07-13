package idir.embag.Ui.Panels.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.DataModels.Products.EStockTypes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class StockPanel extends INodeView  implements  Initializable {

    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh,btnSearch;
    
    @FXML
    private MFXTableView<IProduct> tableStock;

    @FXML
    private MFXComboBox<EStockTypes> comboStockType;

    

    public StockPanel() {
        fxmlPath = "/views/StockPanel.fxml";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboStockType.getItems().addAll(EStockTypes.values());
    }

    @Override
    public Node getView() {
        return root;
    }


}


    

