package idir.embag.EventStore.Models.Session;

import java.util.List;
import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;
import idir.embag.EventStore.Stores.DataStore.IDataDelegate;

public class SessionModel implements IDataDelegate{

    @Override
    public void add(Map<EEventDataKeys,Object> data) {
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void update(int id, Map<EEventDataKeys,Object> data) {
    }

    @Override
    public List<Object> search(Map<EEventDataKeys,Object> data) {
        return null;
    }

    
}
