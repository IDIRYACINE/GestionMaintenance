package idir.embag.Application.Stock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;

@SuppressWarnings("unchecked")
public class FamilyCodesHelper extends IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    public FamilyCodesHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

    @Override
    public void update(IProduct product) {
        IDialogContent dialogContent =  buildUpdateDialog();

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);
        data.put(EEventDataKeys.ProductInstance, product);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void remove(IProduct product) {
        IDialogContent dialogContent =  buildRemoveDialog();

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);
        data.put(EEventDataKeys.ProductInstance, product);

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
            case Add: addTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;
            case Remove: removeTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;  
            case Update: updateTableElement((IProduct)event.getData().get(EEventDataKeys.ProductInstance));
                break;
            case Search: setTableProducts((List<IProduct>)event.getData());
                break;          
              default:
                   break;
           }
        
    }

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
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

    private void setTableProducts(List<IProduct> product){
        tableStock.getItems().setAll(product);
    }

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(IProduct::getArticleName));

        tableStock.getTableColumns().setAll(idColumn,nameColumn);
        
    }

   


    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EEventDataKeys attributes[] = 
        {EEventDataKeys.FamilyCode, EEventDataKeys.FamilyName};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildUpdateDialog(){
        ManagerDialog dialog = new ManagerDialog();

        EEventDataKeys attributes[] = 
        {EEventDataKeys.FamilyCode, EEventDataKeys.FamilyName};

        dialog.setAttributes(attributes);
        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Update, data);
        });

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildRemoveDialog(){
        ConfirmationDialog dialog = new ConfirmationDialog();

        dialog.setOnConfirm(data -> {
            data.remove(EEventDataKeys.DialogContent);
            dispatchEvent(EStores.DataStore, EStoreEvents.StockEvent, EStoreEventAction.Remove, data);
        });
      
        
        dialog.loadFxml();

        return dialog;

    }


    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();



        dialog.loadFxml();

        return dialog;

    }

}
