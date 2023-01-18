package idir.embag.Ui.Dialogs.UsersDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import idir.embag.Application.State.AppState;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Affectation;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Models.Users.RequestsData.LoadRequest;
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
import idir.embag.Ui.Constants.Names;
import idir.embag.Ui.Dialogs.ConfirmationDialog.ConfirmationDialog;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;

@SuppressWarnings("unchecked")
public class UsersManagerController implements IEventSubscriber {

    private MFXTableView<User> usersTable;

    UsersManagerController() {
        StoreCenter.getInstance().subscribeToEvents(EStores.DataStore, EStoreEvents.UsersEvent, this);
    }

    public void notifyActive(MFXTableView<User> usersTable) {
        this.usersTable = usersTable;
        setupTable();
        loadUsers();
    }

    private void setupTable() {

        MFXTableColumn<User> idColumn = new MFXTableColumn<>(Names.Id, true, Comparator.comparing(User::getUserId));
        MFXTableColumn<User> usernnameColumn = new MFXTableColumn<>(Names.Username, true,
                Comparator.comparing(User::getUserName));
        MFXTableColumn<User> passwordColum = new MFXTableColumn<>(Names.Password, true,
                Comparator.comparing(User::getPassword));

        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(User::getUserId));
        usernnameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(User::getUserName));

        passwordColum.setRowCellFactory(product -> new MFXTableRowCell<>(User::getPassword));

        usersTable.getTableColumns().setAll(idColumn, usernnameColumn, passwordColum);

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
        LoadWrapper loadWrapper = new LoadWrapper(1000, 0);
        wrappersData.put(EWrappers.LoadWrapper, loadWrapper);

        Map<EEventsDataKeys, Object> data = new HashMap<>();

        data.put(EEventsDataKeys.WrappersKeys, wrappersData);
        data.put(EEventsDataKeys.Subscriber, this);
        data.put(EEventsDataKeys.Instance, LoadRequest.loadUserRequest());

        StoreEvent event = new StoreEvent(EStoreEvents.UsersEvent, EStoreEventAction.Load, data);
        StoreDispatch action = new StoreDispatch(EStores.DataStore, event);

        StoreCenter.getInstance().dispatch(action);

    }

    public void addUser() {
        // by default this is available for only admins . they have all designations
        // preloaded
        User user = new User(AppState.getInstance().getUserCurrId() , "", null, false, null);
        ArrayList<Affectation> adminDesignations = AppState.getInstance().getCurrentUser().getDesignations();
        addUserDialog(user, adminDesignations);
        
    }

    public void updateUser() {
        User user = usersTable.getSelectionModel().getSelectedValues().get(0);

        if (user != null) {
            ArrayList<Affectation> adminDesignations = AppState.getInstance().getCurrentUser().getDesignations();
            ArrayList<Integer> userDesignations = user.getDesignationsIds();
            ArrayList<Affectation> ungrantedDesignations = new ArrayList<>();

            for (Affectation adminDesignation : adminDesignations) {

               if(!userDesignations.contains(adminDesignation.getAffectationId()))
                   ungrantedDesignations.add(adminDesignation);

            }

            updateUserDialog(user, ungrantedDesignations);
        }
    }

    public void deleteUser() {
        User user = usersTable.getSelectionModel().getSelectedValues().get(0);
        if (user != null) {
            deleteUserDialog(user);
        }

    }

    private void addUserDialog(User user, ArrayList<Affectation> ungrantedDesignations) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        UserEditorDialog dialogContent = new UserEditorDialog(user, ungrantedDesignations);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, user);
        data.put(EEventsDataKeys.Subscriber, this);

        dialogContent.setOnConfirm(other -> {
            other.put(EEventsDataKeys.Instance, user);
            AppState.getInstance().nextWorkerCurrId();

            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Add, other);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,
                data);

    }

    private void updateUserDialog(User user, ArrayList<Affectation> ungrantedDesignations) {
        StoreCenter storeCenter = StoreCenter.getInstance();

        UserEditorDialog dialogContent = new UserEditorDialog(user, ungrantedDesignations);

        Map<EEventsDataKeys, Object> data = new HashMap<>();
        Map<ENavigationKeys, Object> navigationData = new HashMap<>();

        navigationData.put(ENavigationKeys.DialogContent, dialogContent);
        data.put(EEventsDataKeys.NavigationKeys, navigationData);

        data.put(EEventsDataKeys.Instance, user);
        data.put(EEventsDataKeys.Subscriber, this);

        dialogContent.setOnConfirm(other -> {
            other.put(EEventsDataKeys.Instance, user);

            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Update, other);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,
                data);

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
            other.put(EEventsDataKeys.Instance, user);
            storeCenter.dispatchEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Remove, other);

        });

        dialogContent.loadFxml();

        storeCenter.dispatchEvent(EStores.NavigationStore, EStoreEvents.NavigationEvent, EStoreEventAction.Dialog,
                data);

    }

}
