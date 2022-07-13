package idir.embag.EventStore.Stores.DataStore;

import java.util.List;

public interface IDataDelegate {
    public void add(Object data);
    public void remove(int id);
    public void update(int id , Object data);
    public List<Object> search(Object data);
}
