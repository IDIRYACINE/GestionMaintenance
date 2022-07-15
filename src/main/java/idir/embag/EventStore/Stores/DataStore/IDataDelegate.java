package idir.embag.EventStore.Stores.DataStore;

import java.util.List;

public interface IDataDelegate {
    public void add(Object data);
    public void remove(Object data);
    public void update(Object data);
    public List<Object> search(Object data);
}
