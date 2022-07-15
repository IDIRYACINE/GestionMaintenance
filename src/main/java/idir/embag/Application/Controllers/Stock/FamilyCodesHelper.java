package idir.embag.Application.Controllers.Stock;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.DataModels.Metadata.EFamilyCodeAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.IDialogContent;
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
    public void update(IProduct product, int cellIndex) {
        IDialogContent dialogContent =  buildUpdateDialog(product, cellIndex);

        Map<EEventDataKeys,Object> data = new HashMap<>();
        data.put(EEventDataKeys.DialogContent, dialogContent);

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void remove(int id, int cellIndex) {
        IDialogContent dialogContent =  buildRemoveDialog(id,cellIndex);

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

    @Override
    public void notifySelected() {
        tableStock.getItems().clear();
        setColumns();
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
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(IProduct::getArticleName));

        tableStock.getTableColumns().setAll(idColumn,nameColumn);
        
    }

   


    private IDialogContent buildAddDialog(){
        ManagerDialog dialog = new ManagerDialog();

        String attributes[] = 
        {EFamilyCodeAttributes.FamilyCode.toString(), EFamilyCodeAttributes.FamilyName.toString()};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildUpdateDialog(IProduct product,int cellIndex){
        ManagerDialog dialog = new ManagerDialog();

        String attributes[] = 
        {EFamilyCodeAttributes.FamilyName.toString()};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

    private IDialogContent buildRemoveDialog(int articleId,int cellIndex){
        ManagerDialog dialog = new ManagerDialog();

        String attributes[] = 
        {EFamilyCodeAttributes.FamilyName.toString()};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }


    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        String attributes[] = 
        {EFamilyCodeAttributes.FamilyCode.toString()};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog;

    }

}
