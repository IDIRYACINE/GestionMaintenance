package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Others.FamilyCode;
import idir.embag.DataModels.Products.IProduct;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EFamilyCodeAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.FamilyCodeEditor;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import idir.embag.Ui.Dialogs.FilterDialog.FilterDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class FamilyCodesHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<IProduct> tableStock;

    public FamilyCodesHelper(MFXTableView<IProduct> tableStock) {
        this.tableStock = tableStock;
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.FamilyCodeEvent, this);
    }

    @Override
    public void update(IProduct product) {
        FamilyCodeEditor dialogContent =  new FamilyCodeEditor(product);
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Update, requestData);
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

            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        IProduct product = new FamilyCode("", 0);
        FamilyCodeEditor dialogContent =  new FamilyCodeEditor(product);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, product);
            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Add, requestData);
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

        dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Load,data);
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

    private void setTableProducts(Collection<IProduct> product){
        tableStock.getItems().setAll(product);
    }

    private void setColumns(){
        MFXTableColumn<IProduct> idColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(IProduct::getFamilyCode));
		MFXTableColumn<IProduct> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(IProduct::getArticleName));
        
        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getFamilyCode));
        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(IProduct::getArticleName));

        tableStock.getTableColumns().setAll(idColumn,nameColumn);
        
    }

   
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EFamilyCodeAttributes[] attributes = {EFamilyCodeAttributes.FamilyCode, EFamilyCodeAttributes.FamilyName};
        dialog.setAttributes(attributes);

        dialog.setOnConfirm(requestData -> {
            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Search, requestData);
        });

        dialog.loadFxml();
        return dialog;
    }



}
