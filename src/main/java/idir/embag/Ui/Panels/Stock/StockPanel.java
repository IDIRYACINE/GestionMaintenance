package idir.embag.Ui.Panels.Stock;

import java.net.URL;
import java.util.ResourceBundle;

import idir.embag.Application.Controllers.Stock.FamilyCodesHelper;
import idir.embag.Application.Controllers.Stock.IStockHelper;
import idir.embag.Application.Controllers.Stock.InventoryHelper;
import idir.embag.Application.Controllers.Stock.StockController;
import idir.embag.Application.Controllers.Stock.StockHelper;
import idir.embag.DataModels.Metadata.EStockTypes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.IEventSubscriber;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Panels.Generics.INodeView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class StockPanel extends INodeView  implements  Initializable,IEventSubscriber {

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
    }

    private void subscribeToEvents(){

        StoreEvent event = new StoreEvent(EStoreEvents.SubscribtionEvent, EStoreEventAction.Subscribe, this);

        StoreDispatch action = new StoreDispatch(EStores.DataStore,event);
        StoreCenter.getInstance().dispatch(action);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        IStockHelper stockHelper = new StockHelper(tableStock);
        IStockHelper inventoryHelper = new InventoryHelper(tableStock);
        IStockHelper familyCodesHelper = new FamilyCodesHelper(tableStock);

        controller = new StockController(stockHelper, inventoryHelper, familyCodesHelper);
        
        subscribeToEvents();

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

    @Override
    public void notifyEvent(StoreEvent event) {
        controller.notifyEvent(event);
    }


}


    

