package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Products.FamilyCode;
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
public class FamilyCodesHelper extends IStockHelper implements IEventSubscriber{
    
    private MFXTableView<FamilyCode> tableFamilyCodes;

    public FamilyCodesHelper() {
        this.tableFamilyCodes = new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.FamilyCodeEvent, this);
    }

    @Override
    public void update() {
        FamilyCode familyCode = tableFamilyCodes.getSelectionModel().getSelectedValues().get(0);

        FamilyCodeEditor dialogContent =  new FamilyCodeEditor(familyCode);
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, familyCode);
            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void remove() {
        FamilyCode familyCode = tableFamilyCodes.getSelectionModel().getSelectedValues().get(0);

        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        
        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, familyCode);

            dispatchEvent(EStores.DataStore, EStoreEvents.FamilyCodeEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        FamilyCode familyCode = new FamilyCode("", 0);
        FamilyCodeEditor dialogContent =  new FamilyCodeEditor(familyCode);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, familyCode);
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
            case Add: addTableElement((FamilyCode)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove: removeTableElement((FamilyCode)event.getData().get(EEventsDataKeys.Instance));
                break;  
            case Update: updateTableElement((FamilyCode)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Search: setTablefamilyCodes((Collection<FamilyCode>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;    
            case Load: setTablefamilyCodes((Collection<FamilyCode>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;              
              default:
                   break;
           }
        
    }

    @Override
    public void notifySelected() {
        setup();
    }

   
    private void addTableElement(FamilyCode familyCode) {
        tableFamilyCodes.getItems().add(familyCode);
    }

    private void removeTableElement(FamilyCode familyCode){
        int index = tableFamilyCodes.getItems().indexOf(familyCode);
        tableFamilyCodes.getItems().remove(index);
    }

    private void updateTableElement(FamilyCode familyCode){
        int index = tableFamilyCodes.getItems().indexOf(familyCode);
        tableFamilyCodes.getCell(index).updateRow();
    }

    private void setTablefamilyCodes(Collection<FamilyCode> familyCode){
        tableFamilyCodes.getItems().setAll(familyCode);
    }

    private void setup(){
        tableFamilyCodes.setMinWidth(Measures.defaultTablesWidth);
        tableFamilyCodes.setMinHeight(Measures.defaultTablesHeight);
        tableFamilyCodes.setFooterVisible(false);

        MFXTableColumn<FamilyCode> idColumn = new MFXTableColumn<>(Names.FamilyCode, true, Comparator.comparing(FamilyCode::getFamilyCode));
		MFXTableColumn<FamilyCode> nameColumn = new MFXTableColumn<>(Names.FamilyName, true, Comparator.comparing(FamilyCode::getFamilyName));
        
        idColumn.setRowCellFactory(familyCode -> new MFXTableRowCell<>(FamilyCode::getFamilyCode));
        nameColumn.setRowCellFactory(familyCode -> new MFXTableRowCell<>(FamilyCode::getFamilyName));

        tableFamilyCodes.getTableColumns().setAll(idColumn,nameColumn);
        
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

    @Override
    public Node getView() {
        return tableFamilyCodes;
    }



}
