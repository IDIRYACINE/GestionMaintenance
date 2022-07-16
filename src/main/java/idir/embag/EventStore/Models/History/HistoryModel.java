package idir.embag.EventStore.Models.History;

import java.sql.SQLException;
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
    public void add(Object data) {
        try {
            Map<EEventDataKeys,AttributeWrapper> result = (Map<EEventDataKeys, AttributeWrapper>) data;
            sessionQuery.RegisterSessionRecord(result.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Object data) {}

    @Override
    public void update(Object data) {

    }

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;
        /* TODO: implement this 
        try {
            result = sessionQuery.SearchSessionRecord((SearchWrapper) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        return result;
    }
    
}
