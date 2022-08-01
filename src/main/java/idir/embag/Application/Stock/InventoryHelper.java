package idir.embag.Application.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.Editors.InventoryEditor;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class InventoryHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<IProduct> tableStock;

    public InventoryHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.InventoryEvent, this);
    }

    @Override
    public void update(IProduct product) {
        InventoryEditor dialogContent =  new InventoryEditor(product);

        Runnable sucessCallback = () -> {
            updateTableElement(product);
        };

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void remove(IProduct product) {
        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);
        
        Runnable sucessCallback = () -> {
            removeTableElement(product);
        };

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();


        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        IProduct product = new InventoryProduct(0, "", 0, 0, 0, 0, 0);
        InventoryEditor dialogContent =  new InventoryEditor(product);

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        Runnable sucessCallback = () -> {
            addTableElement(product);
        };
        

        dialogContent.setOnConfirm(requestData -> {

            requestData.put(EEventDataKeys.OnSucessCallback, sucessCallback);

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Add, requestData);
        });

        dialogContent.loadFxml();


        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void refresh() {
        dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Load,null);        
    }

    @Override
    public void search() {
        IDialogContent dialogContent =  buildSearchDialog();
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        
        switch(event.getAction()){
            case Add: addTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;
            case Remove: removeTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;  
            case Update: updateTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;
            case Search: setTableProducts((Collection<IProduct>)event.getData().get(EEventDataKeys.ProductsCollection));
                break;          
            case Load: setTableProducts((Collection<IProduct>)event.getData().get(EEventDataKeys.ProductsCollection));
                break;
              default:
                   break;
           }
        
    }


    private void addTableElement(IProduct product) {
        tableStock.getItems().add(product);
    }

    private void removeTableElement(IProduct product){
        int index = tableStock.getItems().indexOf(product);
        tableStock.getItems().remove(index);
    }

    private void updateTableElement(IProduct product){
        int index = tableStock.getItems().indexOf(product);
        tableStock.getCell(index).updateRow();
    }

    private void setTableProducts(Collection<IProduct> product){
        tableStock.getItems().setAll(product);
    }

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(IProduct::getArticleName));
        MFXTableColumn<IProduct> codebarColumn = new MFXTableColumn<>(Names.Codebar, true, Comparator.comparing(IProduct::getArticleCode));

        MFXTableColumn<IProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getFamilyCode));
        MFXTableColumn<IProduct> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(IProduct::getPrice));

		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleCode));
		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,priceColumn);
    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
    }

   
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EEventDataKeys[] attributes = {EEventDataKeys.ArticleId, EEventDataKeys.ArticleName, 
            EEventDataKeys.Price, EEventDataKeys.Quantity};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

}
