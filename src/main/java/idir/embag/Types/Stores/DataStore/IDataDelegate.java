package idir.embag.Types.Stores.DataStore;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventDataKeys;

public interface IDataDelegate {
    public void add(Map<EEventDataKeys,Object> data);
    public void remove(Map<EEventDataKeys,Object> data);
    public void update(Map<EEventDataKeys,Object> data);
    public void search(Map<EEventDataKeys,Object> data);
    public void load(Map<EEventDataKeys,Object> data);
}
