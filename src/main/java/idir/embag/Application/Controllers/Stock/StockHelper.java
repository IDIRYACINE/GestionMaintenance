package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.IProduct;
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
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.FilterDialog.FilterDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class StockHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<IProduct> tableStock;

    public StockHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.StockEvent, this);

    }

    @Override
    public void update(IProduct product) {
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
    public void remove(IProduct product) {
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
        IProduct product = new StockProduct(0, "",  0, 0, 0);
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
        case Add: addTableElement((IProduct)event.getData().get(EEventsDataKeys.Instance));
            break;
        case Remove: removeTableElement((IProduct)event.getData().get(EEventsDataKeys.Instance));
            break;  
        case Update: updateTableElement((IProduct)event.getData().get(EEventsDataKeys.Instance));
            break;
        case Search: setTableProducts((Collection<IProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
            break;          
        case Load: setTableProducts((Collection<IProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
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
		MFXTableColumn<IProduct> stockColumn = new MFXTableColumn<>(Names.Quantity, true, Comparator.comparing(IProduct::getQuantity));
        
		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));
		stockColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getQuantity));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleCode));
		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,priceColumn,stockColumn);

    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
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
    
}