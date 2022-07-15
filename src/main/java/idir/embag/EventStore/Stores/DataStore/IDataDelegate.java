package idir.embag.EventStore.Stores.DataStore;

import java.util.List;
import java.util.Map;
import idir.embag.DataModels.Metadata.EEventDataKeys;

public interface IDataDelegate {
    public void add(Map<EEventDataKeys,Object> data);
    public void remove(int id);
    public void update(int id , Map<EEventDataKeys,Object> data);
    public List<Object> search(Map<EEventDataKeys,Object> data);
}
