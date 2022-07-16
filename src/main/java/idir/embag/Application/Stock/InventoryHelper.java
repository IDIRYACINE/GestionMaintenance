package idir.embag.Application.Stock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Metadata.EProductAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class InventoryHelper extends IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    

    public InventoryHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

    @Override
    public void update(IProduct product) {
        int cellIndex = tableStock.getItems().indexOf(product);
        IDialogContent dialogContent =  buildUpdateDialog(product,cellIndex);
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    @Override
    public void remove(IProduct product) {
        int cellIndex = tableStock.getItems().indexOf(product);
        IDialogContent dialogContent =  buildRemoveDialog(product.getArticleId(),cellIndex);
        
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    @Override
    public void add() {
        IDialogContent dialogContent =  buildAddDialog();
        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
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
            case Add: addTableElement((IProduct)event.getData());
                break;
            case Remove: removeTableElement((int)event.getData().get(EEventDataKeys.Id));
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

    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildUpdateDialog(IProduct product, int cellIndex){
        ManagerDialog dialog = new ManagerDialog();

        EProductAttributes rawAttributes[] = 
        {EProductAttributes.ArticleId, EProductAttributes.ArticleName, EProductAttributes.Price, EProductAttributes.Quantity};
        String[] attributes = EnumAttributesToString(rawAttributes);

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildRemoveDialog(int articleId , int cellIndex){
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
