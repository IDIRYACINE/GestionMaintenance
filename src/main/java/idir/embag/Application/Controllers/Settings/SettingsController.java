package idir.embag.Application.Controllers.Settings;

import java.util.HashMap;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Ui.Dialogs.ExportDialogs.ExportDialog;
import idir.embag.Ui.Dialogs.ExportDialogs.ImportDialog;
import idir.embag.Ui.Dialogs.UsersDialog.UsersManagerDialog;

public class SettingsController {

    public void importData() {
        ImportDialog dialogContent = new ImportDialog();
        Map<EEventsDataKeys,Object> data = new HashMap<>();

        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
        dialogContent.loadFxml();

        storeCenter.dispatch(event);
        
    }

    public void exportData() {
        ExportDialog dialogContent = new ExportDialog();
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
        dialogContent.loadFxml();

        storeCenter.dispatch(event);
    }

    public void manageUsers() {
        UsersManagerDialog dialogContent = new UsersManagerDialog();
        Map<EEventsDataKeys,Object> data = new HashMap<>();
        
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();
        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);


        StoreCenter storeCenter = StoreCenter.getInstance();
        StoreDispatch event = storeCenter.createStoreEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);
        
        dialogContent.loadFxml();

        storeCenter.dispatch(event);
    }

    
}
