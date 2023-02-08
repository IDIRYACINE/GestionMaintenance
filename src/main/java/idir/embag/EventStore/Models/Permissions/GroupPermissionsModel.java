package idir.embag.EventStore.Models.Permissions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Affectation;
import idir.embag.DataModels.Users.AffectationPermission;
import idir.embag.EventStore.Models.Permissions.RequestsData.LoadRequest;
import idir.embag.EventStore.Models.Permissions.RequestsData.UpdateGroup;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.AffectationsRepository;
import idir.embag.Types.Infrastructure.Database.IAffectationssQuery;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class GroupPermissionsModel implements IDataDelegate {
    private IAffectationssQuery affectationsQuery;
    private IGroupPermissionsQuery groupPermissionsQuery;

    private AffectationsRepository affectationRepository;

    public GroupPermissionsModel( 
    AffectationsRepository affectationRepository) {
        this.affectationsQuery = affectationsQuery;
        this.affectationRepository = affectationRepository;
        this.groupPermissionsQuery = groupPermissionsQuery;
    }

    @Override
    public void add(Map<EEventsDataKeys, Object> data) {
        try {

            UpdateGroup request = DataBundler.retrieveValue(data, EEventsDataKeys.RequestData);
            Collection<AffectationPermission> granted = request.getGrantedPermissions();

            if (granted.size() > 0)
                groupPermissionsQuery.GrantGroupPermission(granted);

            notfiyEvent(EStores.DataStore, EStoreEvents.GroupPermissionsEvent, EStoreEventAction.Add, data);
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
            UpdateGroup request = DataBundler.retrieveValue(data, EEventsDataKeys.RequestData);

            Collection<AffectationPermission> ungranted = request.getUnGrantedPermissions();
            if (ungranted.size() > 0)
                groupPermissionsQuery.RevokeGroupPermission(ungranted);

            notfiyEvent(EStores.DataStore, EStoreEvents.GroupPermissionsEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {

        UpdateGroup request = DataBundler.retrieveValue(data, EEventsDataKeys.RequestData);

        Collection<AffectationPermission> ungranted = request.getUnGrantedPermissions();
        if (ungranted.size() > 0)
            remove(data);

        Collection<AffectationPermission> granted = request.getGrantedPermissions();
        if (granted.size() > 0)
            add(data);

    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {
            SearchWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);
            ResultSet resultSet = affectationsQuery.SearchAffectations(wrappers);

            Collection<Affectation> affectations = affectationRepository.resultSetToAffectation(resultSet);
            data.put(EEventsDataKeys.InstanceCollection, affectations);

            notfiyEvent(EStores.DataStore, EStoreEvents.GroupPermissionsEvent, EStoreEventAction.Search, data);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        try {
            LoadRequest loadRequest = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            ResultSet affectationsResultSet = groupPermissionsQuery.LoadGroupUngrantedPermissions(
                    loadRequest.getGroup().getAffectationsIds());

            Collection<Affectation> affectations = affectationRepository
                    .resultSetToAffectation(affectationsResultSet);

            data.put(EEventsDataKeys.InstanceCollection, affectations);

            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Load, data);

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
