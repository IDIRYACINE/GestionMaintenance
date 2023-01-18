package idir.embag.Ui.Panels.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Stock.AffectationHelper;
import idir.embag.Application.Controllers.Stock.FamilyCodesHelper;
import idir.embag.Application.Controllers.Stock.InventoryHelper;
import idir.embag.Application.Controllers.Stock.StockController;
import idir.embag.Application.Controllers.Stock.StockHelper;
import idir.embag.DataModels.Metadata.EStockTypes;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class StockPanel extends INodeView implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private MFXButton btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    @FXML
    private MFXScrollPane tableHolder;

    @FXML
    private MFXComboBox<EStockTypes> comboStockType;

    private StockController controller;

    public StockPanel() {
        fxmlPath = "/views/Panels/StockPanel.fxml";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IStockHelper stockHelper = new StockHelper();
        IStockHelper inventoryHelper = new InventoryHelper();
        IStockHelper familyCodesHelper = new FamilyCodesHelper();
        IStockHelper designationsHelper = new AffectationHelper();

        controller = new StockController(stockHelper, inventoryHelper, familyCodesHelper, designationsHelper);

        controller.selectStockHelper(EStockTypes.Stock);
        comboStockType.getItems().addAll(EStockTypes.values());
        comboStockType.selectFirst();
    }

    @Override
    public Node getView() {
        return root;
    }

    @FXML
    private void onAdd() {
        controller.add();
    }

    @FXML
    private void onEdit() {
        controller.update();
    }

    @FXML
    private void onDelete() {
        controller.remove();
    }

    @FXML
    private void onRefresh() {
        controller.refresh();
    }

    @FXML
    private void onSearch() {
        controller.search();
    }

    @FXML
    private void onStockTypeChanged() {
        EStockTypes stockType = comboStockType.getSelectionModel().getSelectedItem();
        controller.selectStockHelper(stockType);
        setTable();

    }

    private void setTable() {
        tableHolder.setContent(controller.getTableView());
    }
}