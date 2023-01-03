package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.InventoryProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EInventoryAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.InventoryEditor;
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
public class InventoryHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<InventoryProduct> tableInventory;

    public InventoryHelper() {
        this.tableInventory = new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.InventoryEvent, this);
    }

    @Override
    public void update() {
        InventoryProduct product = tableInventory.getSelectionModel().getSelectedValues().get(0);

        InventoryEditor dialogContent =  new InventoryEditor(product);

        Map<EEventsDataKeys,Object> data = new HashMap<>();

         Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

       

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void remove() {
        InventoryProduct product = tableInventory.getSelectionModel().getSelectedValues().get(0);

        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
           

            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        InventoryProduct product = new InventoryProduct(0, "", 0, 0, 0, 0, 0);
        InventoryEditor dialogContent =  new InventoryEditor(product);

        Map<EEventsDataKeys,Object> data = new HashMap<>();

         Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Add, requestData);
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

        dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Load,data);        
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
            case Add: addTableElement((InventoryProduct)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove: removeTableElement((InventoryProduct)event.getData().get(EEventsDataKeys.Instance));
                break;  
            case Update: updateTableElement((InventoryProduct)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Search: setTableProducts((Collection<InventoryProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;          
            case Load: setTableProducts((Collection<InventoryProduct>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;
              default:
                   break;
           }
        
    }


    private void addTableElement(InventoryProduct product) {
        tableInventory.getItems().add(product);
    }

    private void removeTableElement(InventoryProduct product){
        int index = tableInventory.getItems().indexOf(product);
        tableInventory.getItems().remove(index);
    }

    private void updateTableElement(InventoryProduct product){
        int index = tableInventory.getItems().indexOf(product);
        tableInventory.getCell(index).updateRow();
    }

    private void setTableProducts(Collection<InventoryProduct> product){
        tableInventory.getItems().setAll(product);
    }

    private void setup(){
        tableInventory.setMinWidth(Measures.defaultTablesWidth);
        tableInventory.setMinHeight(Measures.defaultTablesHeight);
        tableInventory.setFooterVisible(false);

        MFXTableColumn<InventoryProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(InventoryProduct::getArticleId));
		MFXTableColumn<InventoryProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(InventoryProduct::getArticleName));
        MFXTableColumn<InventoryProduct> codebarColumn = new MFXTableColumn<>(Names.Codebar, true, Comparator.comparing(InventoryProduct::getArticleCode));

        MFXTableColumn<InventoryProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(InventoryProduct::getFamilyCode));
        MFXTableColumn<InventoryProduct> designationColumn = new MFXTableColumn<>(Names.DesignationId, true, Comparator.comparing(InventoryProduct::getDesignationId));

		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(InventoryProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(InventoryProduct::getArticleName));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(InventoryProduct::getArticleCode));
		designationColumn.setRowCellFactory(product -> new MFXTableRowCell<>(InventoryProduct::getDesignationId));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(InventoryProduct::getFamilyCode));

        tableInventory.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,designationColumn);
    }

    @Override
    public void notifySelected() {
        setup();
    }

   
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EInventoryAttributes[] attributes = {EInventoryAttributes.ArticleId, EInventoryAttributes.StockId
            ,EInventoryAttributes.ArticleCode};

        dialog.setAttributes(attributes);
        
        dialog.setOnConfirm(new Consumer<Map<EEventsDataKeys,Object>> (){
            @Override
            public void accept(Map<EEventsDataKeys,Object> data) {
                dispatchEvent(EStores.DataStore, EStoreEvents.InventoryEvent, EStoreEventAction.Search, data);
            }
        });

        dialog.loadFxml();

        return dialog;

    }

    @Override
    public Node getView() {
        return tableInventory;
    }

}
