package idir.embag.Application.Controllers.Stock;

import java.util.Comparator;

import idir.embag.DataModels.Metadata.EFamilyCodeAttributes;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.EStores;
import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.EventStore.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Ui.Components.FilterDialog.FilterDialog;
import idir.embag.Ui.Components.MangerDialog.ManagerDialog;
import idir.embag.Ui.Constants.Names;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.scene.Node;

@SuppressWarnings("unchecked")
public class FamilyCodesHelper implements IStockHelper{
    
    private MFXTableView<IProduct> tableStock;

    public FamilyCodesHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
    }

    @Override
    public void update(IProduct product) {
        Node dialogContent =  buildUpdateDialog();

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);

        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().dispatch(action);
        
    }

    @Override
    public void remove(int id) {

        Node dialogContent =  buildRemoveDialog();

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);

        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().dispatch(action);
        
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search() {
        Node dialogContent =  buildSearchDialog();

        StoreEvent event = new StoreEvent(EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,dialogContent);

        StoreDispatch action = new StoreDispatch(EStores.NavigationStore, event);

        StoreCenter.getInstance().dispatch(action);
        
    }


    @Override
    public void notifyEvent(StoreEvent event) {
        switch(event.getAction()){
            case Add: addTableElement();
                break;
            case Remove: removeTableElement();
                break;  
            case Update: updateTableElement();
                break;
            case Search: setTableElements();
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

    private void addTableElement(){}
    private void removeTableElement(){}
    private void updateTableElement(){}
    private void setTableElements(){}

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getArticleId));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(IProduct::getArticleName));

        tableStock.getTableColumns().setAll(idColumn,nameColumn);
        
    }

   


    private Node buildAddDialog(){
        ManagerDialog<EFamilyCodeAttributes> dialog = new ManagerDialog<>();

        EFamilyCodeAttributes attributes[] = 
        {EFamilyCodeAttributes.FamilyCode, EFamilyCodeAttributes.FamilyName};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }

    private Node buildUpdateDialog(){
        ManagerDialog<EFamilyCodeAttributes> dialog = new ManagerDialog<>();

        EFamilyCodeAttributes attributes[] = 
        {EFamilyCodeAttributes.FamilyName};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }

    private Node buildRemoveDialog(){
        ManagerDialog<EFamilyCodeAttributes> dialog = new ManagerDialog<>();

        EFamilyCodeAttributes attributes[] = 
        {EFamilyCodeAttributes.FamilyName};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }


    private Node buildSearchDialog(){
        FilterDialog<EFamilyCodeAttributes> dialog = new FilterDialog<>();

        EFamilyCodeAttributes attributes[] = 
        {EFamilyCodeAttributes.FamilyCode};

        dialog.setAttributes(attributes);

        dialog.loadFxml();

        return dialog.getView();

    }

}
