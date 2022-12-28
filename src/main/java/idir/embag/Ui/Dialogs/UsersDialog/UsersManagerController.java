package idir.embag.Ui.Dialogs.UsersDialog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.MetaData.ENavigationKeys;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.Generics.IEventSubscriber;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;
import idir.embag.Ui.Constants.Messages;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class UsersManagerController implements IEventSubscriber {

    private MFXTableView<User> usersTable;

    public void notifyActive(MFXTableView<User> usersTable) {
        this.usersTable = usersTable;
        loadUsers();
    }

    @Override
    public void notifyEvent(StoreEvent event) {
        switch (event.getAction()) {
            case Load:
                setElements(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.InstanceCollection));
                break;
            case Add:
                addElement(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;

            case Remove:
                removeElement(DataBundler.retrieveValue(event.getData(), EEventsDataKeys.Instance));
                break;
            default:
                break;
        }

    }

    private void addElement(User user) {
        usersTable.getItems().add(user);
    }

    private void removeElement(User user) {
        usersTable.getItems().remove(user);
    }

    private void setElements(Collection<User> users) {
        usersTable.getItems().clear();
        usersTable.getItems().addAll(users);
    }

    private void loadUsers() {
        Map<EWrappers, Object> wrappersData = new HashMap<>();
        LoadWrapper loadWrapper = new LoadWrapper(100, 0);
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        data.put(EEventsDataKeys.Subscriber, this);

        StoreEvent event = new StoreEvent(EStoreEvents.UsersEvent, EStoreEventAction.Load, data);
        StoreDispatch action = new StoreDispatch(EStores.DataStore, event);

        StoreCenter.getInstance().dispatch(action);

    }

    public void addUser() {
        User user = new User(0, null, null, false, null);
        addUserDialog(user);
    }

    public void updateUser() {
        User user = usersTable.getSelectionModel().getSelectedValues().get(0);
        if (user != null) {
            updateUserDialog(user);
        }
    }

    public void deleteUser() {
        User user = usersTable.getSelectionModel().getSelectedValues().get(0);
        if (user != null) {
            deleteUserDialog(user);
        }

    }

    private void addUserDialog(User user) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        UserEditorDialog dialogContent = new UserEditorDialog(user);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, user);
        data.put(EEventsDataKeys.Subscriber, this);

        dialogContent.setOnConfirm(other -> {
            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Add, data);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

   
    }

    private void updateUserDialog(User user) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        UserEditorDialog dialogContent = new UserEditorDialog(user);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, user);
        data.put(EEventsDataKeys.Subscriber, this);

        dialogContent.setOnConfirm(other -> {
            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Update, data);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }

    private void deleteUserDialog(User user) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        ConfirmationDialog dialogContent = new ConfirmationDialog();
        dialogContent.setMessage(Messages.deleteElement);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, user);
        data.put(EEventsDataKeys.Subscriber, this);

        dialogContent.setOnConfirm(other -> {
            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Remove, data);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog, data);

    }


}
