package idir.embag.Application.Controllers.Stock;

import java.util.Comparator;
import java.util.List;

import idir.embag.DataModels.Metadata.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Components.IDialogContent;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class InventoryHelper implements IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    

    public InventoryHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

    @Override
    public void update(IProduct product) {
        IDialogContent dialogContent =  buildUpdateDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
    }

    @Override
    public void remove(int id) {
        IDialogContent dialogContent =  buildRemoveDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);   
    }

    @Override
    public void add() {
        IDialogContent dialogContent =  buildAddDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search() {
        IDialogContent dialogContent =  buildSearchDialog();
        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);
        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);
        StoreCenter.getInstance().dispatch(action);
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        
        switch(event.getAction()){
            case Add: addTableElement((IProduct)event.getData());
                break;
            case Remove: removeTableElement((int)event.getData());
                break;  
            case Update: updateTableElement();
                break;
            case Search: setTableProducts((List<IProduct>)event.getData());
                break;          
              default:
                   break;
           }
        
    }


    private void addTableElement(IProduct product) {
        tableStock.getItems().add(product);
    }

    private void removeTableElement(int index){
        tableStock.getItems().remove(index);
    }

    private void updateTableElement(){
        //TODO : implement
    }

    private void setTableProducts(List<IProduct> product){
        tableStock.getItems().setAll(product);
    }

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.ArticleId, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.ArticleName, true, Comparator.comparing(IProduct::getArticleName));
        MFXTableColumn<IProduct> codebarColumn = new MFXTableColumn<>(Names.Codebar, true, Comparator.comparing(IProduct::getArticleCode));

        MFXTableColumn<IProduct> familyColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getFamilyCode));
        MFXTableColumn<IProduct> priceColumn = new MFXTableColumn<>(Names.Price, true, Comparator.comparing(IProduct::getStockPrice));

		idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleId));
		nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));

        codebarColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleCode));
		priceColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getStockPrice));
		familyColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));

        tableStock.getTableColumns().setAll(idColumn,codebarColumn,nameColumn,familyColumn,priceColumn);
    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
    }

    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildUpdateDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildRemoveDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }


    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }



    private String[] EnumAttributesToString(EProductAttributes[] attributes){
        String[] result = new String[attributes.length];
        for (int i = 0 ; i < attributes.length ;i++){
            result[i] = attributes[i].toString();
        }
        return result;
    }
    
}
