package idir.embag.Ui.Panels.Stock;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Stock.FamilyCodesHelper;
import idir.embag.Application.Controllers.Stock.IStockHelper;
import idir.embag.Application.Controllers.Stock.InventoryHelper;
import idir.embag.Application.Controllers.Stock.StockController;
import idir.embag.Application.Controllers.Stock.StockHelper;
import idir.embag.DataModels.Metadata.EStockTypes;
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

    private StockController controller;

    public StockPanel() {
        fxmlPath = "/views/StockPanel.fxml";
        IStockHelper stockHelper = new StockHelper();
        
        IStockHelper inventoryHelper = new InventoryHelper();
        IStockHelper familyCodesHelper = new FamilyCodesHelper();

        controller = new StockController(stockHelper, inventoryHelper, familyCodesHelper);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller.selectStockHelper(EStockTypes.Stock);
        comboStockType.getItems().addAll(EStockTypes.values());
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onAdd(){
        controller.add();
    }

    @FXML
    private void onEdit(){
        IProduct product = tableStock.getSelectionModel().getSelectedValues().get(0);
        controller.update(product);
    }

    @FXML
    private void onDelete(){
        IProduct product = tableStock.getSelectionModel().getSelectedValues().get(0);
        controller.remove(product.getArticleId());
    }

    @FXML
    private void onRefresh(){
        controller.refresh();
    }

    @FXML
    private void onSearch(){
        controller.search();
    }

    @FXML
    private void onStockTypeChanged(){
        EStockTypes stockType = comboStockType.getSelectionModel().getSelectedItem();
        controller.selectStockHelper(stockType);
    }

}


    

