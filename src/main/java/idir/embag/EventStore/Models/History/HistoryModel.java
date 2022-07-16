package idir.embag.EventStore.Models.History;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.Types.Infrastructure.Database.ISessionQuery;
import idir.embag.Types.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Types.Stores.DataStore.IDataDelegate;

@SuppressWarnings("unchecked")
public class HistoryModel implements IDataDelegate{

    ISessionQuery sessionQuery;
    
    public HistoryModel(ISessionQuery sessionQuery) {
        this.sessionQuery = sessionQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        try {
            sessionQuery.RegisterSessionRecord((Collection<AttributeWrapper>) data.get(EEventDataKeys.SessionRecordInstance));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Map<EEventDataKeys,Object> data) {}

    @Override
    public void update(Map<EEventDataKeys,Object> data) {

    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        List<Object> result = null;
        return result;
    }
    
}
