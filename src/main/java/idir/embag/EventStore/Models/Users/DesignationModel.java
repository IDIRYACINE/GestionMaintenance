package idir.embag.EventStore.Models.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import idir.embag.Application.Utility.DataBundler;
import idir.embag.DataModels.Metadata.EEventsDataKeys;
import idir.embag.DataModels.Users.Affectation;
import idir.embag.EventStore.Stores.StoreCenter.StoreCenter;
import idir.embag.Repository.AffectationsRepository;
import idir.embag.Types.Infrastructure.Database.IAffectationssQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.LoadWrapper;
import idir.embag.Types.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Types.MetaData.EWrappers;
import idir.embag.Types.Stores.DataStore.IDataDelegate;
import idir.embag.Types.Stores.Generics.StoreDispatch.EStores;
import idir.embag.Types.Stores.Generics.StoreDispatch.StoreDispatch;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEventAction;
import idir.embag.Types.Stores.Generics.StoreEvent.EStoreEvents;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public class DesignationModel implements IDataDelegate {
    private IAffectationssQuery designationsQuery;
    private AffectationsRepository designationsRepository;

    public DesignationModel(IAffectationssQuery designationsQuery, AffectationsRepository designationsRepository) {
        this.designationsQuery = designationsQuery;
        this.designationsRepository = designationsRepository;
    }

    @Override
    public void add(Map<EEventsDataKeys, Object> data) {
        try {
            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.AttributesCollection);

            designationsQuery.RegisterAffectation(wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Add, data);
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
            Affectation designation = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);

            designationsQuery.DeleteAffectation(designation.getAffectationId());
            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Remove, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Map<EEventsDataKeys, Object> data) {
        try {
            Affectation designation = DataBundler.retrieveValue(data, EEventsDataKeys.Instance);
            Collection<AttributeWrapper> wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.AttributesCollection);

            designationsQuery.UpdateAffectation(designation.getAffectationId(), wrappers);
            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Update, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void search(Map<EEventsDataKeys, Object> data) {
        try {
            SearchWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.SearchWrapper);
            ResultSet resultSet = designationsQuery.SearchAffectations(wrappers);

            Collection<Affectation> designations = designationsRepository.resultSetToAffectation(resultSet);
            data.put(EEventsDataKeys.InstanceCollection, designations);

            notfiyEvent(EStores.DataStore, EStoreEvents.DesignationEvent, EStoreEventAction.Search, data);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void load(Map<EEventsDataKeys, Object> data) {
        try {
            LoadWrapper wrappers = DataBundler.retrieveNestedValue(data, EEventsDataKeys.WrappersKeys,
                    EWrappers.LoadWrapper);

            ResultSet resultSet = designationsQuery.LoadAffectations(wrappers);

            Collection<Affectation> designations = designationsRepository.resultSetToAffectation(resultSet);

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
