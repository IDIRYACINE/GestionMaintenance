package idir.embag.EventStore.Models.History;

import java.sql.SQLException;
import java.util.List;

import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.ISessionQuery;
import idir.embag.Infrastructure.Database.Generics.AttributeWrapper;
import idir.embag.Infrastructure.Database.Generics.SearchWrapper;
import idir.embag.Infrastructure.Database.Generics.MDatabase.SessionsRecordsAttributes;

@SuppressWarnings("unchecked")
public class HistoryModel implements IDataDelegate{

    ISessionQuery sessionQuery;
    
    public HistoryModel(ISessionQuery sessionQuery) {
        this.sessionQuery = sessionQuery;
    }

    @Override
    public void add(Object data) {
        
        try {
            sessionQuery.RegisterSessionRecord((AttributeWrapper<SessionsRecordsAttributes>[]) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {}

    @Override
    public void update(int id, Object data) {}

    @Override
    public List<Object> search(Object data) {
        List<Object> result = null;

        try {
            result = sessionQuery.SearchSessionRecord((SearchWrapper) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
}
