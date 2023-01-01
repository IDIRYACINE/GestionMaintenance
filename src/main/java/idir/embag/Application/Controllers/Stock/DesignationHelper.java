package idir.embag.Application.Controllers.Stock;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Designation;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Application.Stock.IStockHelper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Metadata.EDesignationAttributes;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Panels.Components.IDialogContent;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Components.Editors.DesignationEditor;
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

public class DesignationHelper extends IStockHelper implements IEventSubscriber{
    private MFXTableView<Designation> tableDesignations;

    public DesignationHelper() {
        this.tableDesignations = new MFXTableView<>();
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.DesignationEvent, this);
    }



    @Override
    public void update() {
        Designation designation = tableDesignations.getSelectionModel().getSelectedValues().get(0);

        DesignationEditor dialogContent =  new DesignationEditor(designation);
        
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, designation);
            dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Update, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
    }

    @Override
    public void remove() {
        Designation designation = tableDesignations.getSelectionModel().getSelectedValues().get(0);

        ConfirmationDialog dialogContent =  new ConfirmationDialog();

        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        
        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, designation);

            dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Remove, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }

    @Override
    public void add() {
        Designation designation = new Designation(0, "");
        DesignationEditor dialogContent =  new DesignationEditor(designation);

        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        dialogContent.setOnConfirm(requestData -> {
            requestData.put(EEventsDataKeys.Instance, designation);
            dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Add, requestData);
        });

        dialogContent.loadFxml();

        dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
    }
    
    @Override
    public void refresh() {
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(1000,0);
        
        Map<EWrappers,Object> wrappersData = new HashMap<>();
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);
        data.put(EEventsDataKeys.WrappersKeys, wrappersData);

        dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Load,data);
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
            case Add: addTableElement((Designation)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Remove: removeTableElement((Designation)event.getData().get(EEventsDataKeys.Instance));
                break;  
            case Update: updateTableElement((Designation)event.getData().get(EEventsDataKeys.Instance));
                break;
            case Search: setTableDesignations((Collection<Designation>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;    
            case Load: setTableDesignations((Collection<Designation>)event.getData().get(EEventsDataKeys.InstanceCollection));
                break;              
              default:
                   break;
           }
        
    }

    @Override
    public void notifySelected() {
        setup();
    }

   
    private void addTableElement(Designation designation) {
        tableDesignations.getItems().add(designation);
    }

    private void removeTableElement(Designation designation){
        int index = tableDesignations.getItems().indexOf(designation);
        tableDesignations.getItems().remove(index);
    }

    private void updateTableElement(Designation designation){
        int index = tableDesignations.getItems().indexOf(designation);
        tableDesignations.getCell(index).updateRow();
    }

    private void setTableDesignations(Collection<Designation> designation){
        tableDesignations.getItems().setAll(designation);
    }

    private void setup(){
        tableDesignations.setMinWidth(Measures.defaultTablesWidth);
        tableDesignations.setMinHeight(Measures.defaultTablesHeight);
        tableDesignations.setFooterVisible(false);

        MFXTableColumn<Designation> idColumn = new MFXTableColumn<>(Names.Id, true, Comparator.comparing(Designation::getDesignationId));
		MFXTableColumn<Designation> nameColumn = new MFXTableColumn<>(Names.DesignationName, true, Comparator.comparing(Designation::getDesignationName));
        
        idColumn.setRowCellFactory(designations -> new MFXTableRowCell<>(Designation::getDesignationId));
        nameColumn.setRowCellFactory(designations -> new MFXTableRowCell<>(Designation::getDesignationName));

        tableDesignations.getTableColumns().setAll(idColumn,nameColumn);
        
    }

   
    private IDialogContent buildSearchDialog(){
        FilterDialog dialog = new FilterDialog();

        EDesignationAttributes[] attributes = {EDesignationAttributes.DesignationId, EDesignationAttributes.DesignationName};
        dialog.setAttributes(attributes);

        dialog.setOnConfirm(requestData -> {
            dispatchEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Search, requestData);
        });

        dialog.loadFxml();
        return dialog;
    }

    @Override
    public Node getView() {
        return tableDesignations;
    }

}
