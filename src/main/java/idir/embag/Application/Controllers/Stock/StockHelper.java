package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.StockProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EStockAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.StockEditor;
import idir.embag.Ui.Constants.Measures;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.FilterDialog.FilterDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.scene.Node;

@SuppressWarnings("unchecked")
public class StockHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<StockProduct> tableStock;

    public StockHelper() {
        this.tableStock = new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.StockEvent, this);

    }

    @Override
    public void update() {
        StockProduct product = tableStock.getSelectionModel().getSelectedValues().get(0);

        StockEditor dialogContent =  new StockEditor(product);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();


        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void remove() {
        StockProduct product = tableStock.getSelectionModel().getSelectedValues().get(0);

        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);

            dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        StockProduct product = new StockProduct(0, "",  0, 0, 0);
        StockEditor dialogContent =  new StockEditor(product);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Add, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void refresh() {
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100,0);

        Map<EWrappers,Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        
        dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Load,data);
    }

    @Override
    public void search() {
        IDialogContent dialogContent =  buildSearchDialog();
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }


    @Override
    public void notifyEvent(StoreEvent event) {
       switch(event.getAction()){
        case Add: addTableElement((StockProduct)event.getData().get(EEventsDataKeys.Instance));
            break;
        case Remove: removeTableElement((StockProduct)event.getData().get(EEventsDataKeys.Instance));
            break;  
        case Update: updateTableElement((StockProduct)event.getData().get(EEventsDataKeys.Instance));
            break;
        case Search: setTableProducts((Collection<StockProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
            break;          
        case Load: setTableProducts((Collection<StockProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;
        default:
            break;
       }
    }

    private void addTableElement(StockProduct product) {
        tableStock.getItems().add(product);
    }

    private void removeTableElement(StockProduct product){
        int index = tableStock.getItems().indexOf(product);
        tableStock.getItems().remove(index);
    }

    private void updateTableElement(StockProduct product){
        int index = tableStock.getItems().indexOf(product);
        tableStock.getCell(index).updateRow();
    }

    private void setTableProducts(Collection<StockProduct> product){
        tableStock.getItems().setAll(product);
    }

    private void setup(){
        tableStock.setMinWidth(Measures.defaultTablesWidth);
        tableStock.setMinHeight(Measures.defaultTablesHeight);
        tableStock.setFooterVisible(false);
        
        MFXTableColumn<StockProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(StockProduct::getArticleId));
		MFXTableColumn<StockProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(StockProduct::getArticleName));
        MFXTableColumn<StockProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(StockProduct::getFamilyCode));
        MFXTableColumn<StockProduct> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(StockProduct::getPrice));
		MFXTableColumn<StockProduct> stockColumn = new MFXTableColumn<>(Names.Quantity, true, Comparator.comparing(StockProduct::getQuantity));
        
		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(StockProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(StockProduct::getArticleName));
		stockColumn.setRowCellFactory(product -> new MFXTableRowCell<>(StockProduct::getQuantity));

		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(StockProduct::getPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(StockProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,nameColumn,familyColumn,priceColumn,stockColumn);

    }

    @Override
    public void notifySelected() {
        setup();
    }

   
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();
        
        EStockAttributes[] attributes = {EStockAttributes.ArticleId, EStockAttributes.ArticleName,
            EStockAttributes.Price, EStockAttributes.Quantity};

        dialog.setOnConfirm(new Consumer<Map<EEventsDataKeys,Object>> (){
            @Override
            public void accept(Map<EEventsDataKeys,Object> data) {
                dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Search, data);
            }
        });

        dialog.setAttributes(attributes);
        dialog.loadFxml();
        return dialog;
    }

    @Override
    public Node getView() {
        return tableStock;
    }
    
}