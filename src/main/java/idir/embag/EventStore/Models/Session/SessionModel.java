package idir.embag.EventStore.Models.Session;

import java.util.List;

import idir.embag.EventStore.Stores.Generics.IDataDelegate;

public class SessionModel implements IDataDelegate{

    @Override
    public void add(Object data) {
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void update(int id, Object data) {
    }

    @Override
    public List<Object> search(Object data) {
        return null;
    }

    
}
