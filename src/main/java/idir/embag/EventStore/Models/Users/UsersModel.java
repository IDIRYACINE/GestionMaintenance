package idir.embag.EventStore.Models.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Affectation;
import idir.embag.DataModels.Users.User;
import idir.embag.EventStore.Models.Users.RequestsData.LoadRequest;
import idir.embag.EventStore.Models.Users.RequestsData.UpdateUser;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.AffectationsRepository;
import idir.embag.Repository.UsersRepository;
import idir.embag.Types.Infrastructure.Database.IAffectationssQuery;
import idir.embag.Types.Infrastructure.Database.IUsersQuery;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class UsersModel implements IDataDelegate {

    private IUsersQuery usersQuery;
    private UsersRepository usersRepository;
    private AffectationsRepository designationRepository;
    private IAffectationssQuery designationsQuery;

    public UsersModel( UsersRepository usersRepository,AffectationsRepository designationsRepository) {
        this.usersQuery = usersQuery;
        this.usersRepository = usersRepository;
        this.designationRepository = designationsRepository;
    }

    @Override
    public void add(Map<EEventsDataKeys, Object> data) {
        try {
            UpdateUser userData = DataBundler.retrieveValue(data, EEventsDataKeys.RequestData);

            usersQuery.RegisterUser(userData.getFields());

            usersQuery.GrantDesignationSupervisior(userData.getGrantedPermissions());

            notfiyEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Add, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void importCollection(Map<EEventsDataKeys, Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Map<EEventsDataKeys, Object> data) {
        try {
            User user = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            usersQuery.UnregisterUser(user.getUserId());
            notfiyEvent(EStores.DataStore, EStoreEvents.UsersEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {
        try {
            User user = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            UpdateUser userData = DataBundler.retrieveValue(data, EEventsDataKeys.RequestData);

            if (!userData.getFields().isEmpty())
                usersQuery.UpdateUser(user.getUserId(), userData.getFields());

            if (!userData.getGrantedPermissions().isEmpty())
                usersQuery.GrantDesignationSupervisior(userData.getGrantedPermissions());

            if (!userData.getUnGrantedPermissions().isEmpty())
                usersQuery.RevokeDesignationSupervisior(userData.getUnGrantedPermissions());

            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationPermissionEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {
            SearchWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);
            ResultSet resultSet = usersQuery.SearchUsers(wrappers);

            Collection<User> users = usersRepository.resultSetToUsers(resultSet);

            for (User user : users) {
                fetchAndSetUserDesignations(user);
            }

            data.put(EEventsDataKeys.InstanceCollection, users);

            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Search, data);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void fetchAndSetUserDesignations(User user) {

        ResultSet designationsResultSet;
        ArrayList<Affectation> designations;
        try {
            if (user.isAdmin()) {
                LoadWrapper loadWrapper = new LoadWrapper(1000, 0);
                designationsResultSet = designationsQuery.LoadAffectations(loadWrapper);
                designations = designationRepository.resultSetToAffectation(designationsResultSet);
                user.setDesignations(designations);

                return;
            }

            designationsResultSet = usersQuery.LoadUserPermissions(user.getUserId());
            designations = designationRepository.resultSetToAffectation(designationsResultSet);
            user.setDesignations(designations);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        try {
            LoadRequest loadRequest = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            if (loadRequest.isLoadUsers()) {
                LoadWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                        EWrappers.LoadWrapper);

                ResultSet usersSet = usersQuery.LoadUsers(wrappers);

                Collection<User> users = usersRepository.resultSetToUsers(usersSet);

                for (User user : users) {
                    fetchAndSetUserDesignations(user);

                }

                data.put(EEventsDataKeys.InstanceCollection, users);

                notfiyEvent(EStores.DataStore, EStoreEvents.DesignationPermissionEvent, EStoreEventAction.Load, data);

            }

            else if (loadRequest.isLoadUserUngrantedDesignations()) {

                ResultSet designationsResultSet = usersQuery.LoadUserUngrantedPermissions(
                        loadRequest.getUser().getDesignationsIds());

                Collection<Affectation> designations = designationRepository
                        .resultSetToAffectation(designationsResultSet);

                data.put(EEventsDataKeys.InstanceCollection, designations);

                notfiyEvent(EStores.DataStore, EStoreEvents.DesignationPermissionEvent, EStoreEventAction.Load, data);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private void notfiyEvent(EStores store, EStoreEvents storeEvent, EStoreEventAction actionEvent,
            Map<EEventsDataKeys, Object> data) {
        StoreEvent event = new StoreEvent(storeEvent, actionEvent, data);
        StoreDispatch action = new StoreDispatch(store, event);
        StoreCenter.getInstance().notify(action);
    }

}
