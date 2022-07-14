package idir.embag.Application.Controllers.Stock;

import java.util.Comparator;
import idir.embag.DataModels.Metadata.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.scene.Node;

@SuppressWarnings("unchecked")
public class StockHelper implements IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    public StockHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

    @Override
    public void update(IProduct product) {
        
        
    }

    @Override
    public void remove(int id) {
        
        
    }

    @Override
    public void add() {

        Node dialogContent =  buildAddDialog();

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);

        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().dispatch(action);
        
    }

    @Override
    public void refresh() {
        
        
    }

    @Override
    public void search() {
        
    }

    private Node buildAddDialog(){
        ManagerDialog<EProductAttributes> dialog = new ManagerDialog<>();

        EProductAttributes attributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }

    @Override
    public void notifyEvent(StoreEvent event) {

       switch(event.getAction()){
        case Add: addTableProduct();
            break;
        case Remove: removeTableProduct();
            break;  
        case Update: updateTableProduct();
            break;
        case Search: setTableProducts();
            break;          
          default:
               break;
       }
        
    }

    private void addTableProduct(){}

    private void removeTableProduct(){}

    private void updateTableProduct(){}

    private void setTableProducts(){}

    private void setColumns(){
       
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(IProduct::getArticleName));
        MFXTableColumn<IProduct> codebarColumn = new MFXTableColumn<>(Names.Codebar, true, Comparator.comparing(IProduct::getArticleCode));

        MFXTableColumn<IProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getFamilyCode));
        MFXTableColumn<IProduct> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(IProduct::getStockPrice));
		MFXTableColumn<IProduct> stockColumn = new MFXTableColumn<>(Names.Quantity, true, Comparator.comparing(IProduct::getStockQuantity));

		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));
		stockColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getStockPrice));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleCode));
		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getStockPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,priceColumn,stockColumn);

    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
    }
    
}
