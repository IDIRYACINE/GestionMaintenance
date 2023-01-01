package idir.embag.EventStore.Models.Permissions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Designation;
import idir.embag.DataModels.Users.DesignationPermission;
import idir.embag.EventStore.Models.Permissions.RequestsData.LoadRequest;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.DesignationsRepository;
import idir.embag.Types.Infrastructure.Database.IDesignationsQuery;
import idir.embag.Types.Infrastructure.Database.IGroupPermissionsQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class GroupPermissionsModel implements IDataDelegate {
    private IDesignationsQuery designationsQuery;
    private IGroupPermissionsQuery groupPermissionsQuery;

    private DesignationsRepository designationsRepository;

    public GroupPermissionsModel(IDesignationsQuery designationsQuery, IGroupPermissionsQuery groupPermissionsQuery,
            DesignationsRepository designationsRepository) {
        this.designationsQuery = designationsQuery;
        this.designationsRepository = designationsRepository;
        this.groupPermissionsQuery = groupPermissionsQuery;
    }

    @Override
    public void add(Map<EEventsDataKeys, Object> data) {
        try {
            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.AttributesCollection);

            groupPermissionsQuery.GrantGroupPermission(wrappers);

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

            Collection<DesignationPermission> designation = DataBundler.retrieveValue(data,
                    EEventsDataKeys.InstanceCollection);

            groupPermissionsQuery.RevokeGroupPermission(designation);

            notfiyEvent(EStores.DataStore, EStoreEvents.GroupPermissionsEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {
            SearchWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);
            ResultSet resultSet = designationsQuery.SearchDesignations(wrappers);

            Collection<Designation> designations = designationsRepository.resultSetToDesignation(resultSet);
            data.put(EEventsDataKeys.InstanceCollection, designations);

            notfiyEvent(EStores.DataStore, EStoreEvents.GroupPermissionsEvent, EStoreEventAction.Search, data);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        try {
            LoadRequest loadRequest = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            ResultSet designationsResultSet = groupPermissionsQuery.LoadGroupUngrantedPermissions(
                    loadRequest.getGroup().getDesignationsIds());

            Collection<Designation> designations = designationsRepository
                    .resultSetToDesignation(designationsResultSet);

            data.put(EEventsDataKeys.InstanceCollection, designations);

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
