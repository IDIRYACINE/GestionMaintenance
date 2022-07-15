package idir.embag.EventStore.Models.History;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;
import idir.embag.Infrastructure.Database.ISessionQuery;

public class HistoryModel implements IDataDelegate{

    ISessionQuery sessionQuery;
    
    public HistoryModel(ISessionQuery sessionQuery) {
        this.sessionQuery = sessionQuery;
    }

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
        
        try {
            sessionQuery.RegisterSessionRecord(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) {}

    @Override
    public void update(int id, Map<EEventDataKeys,Object> data) {

    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
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
