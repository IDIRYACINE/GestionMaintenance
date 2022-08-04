package idir.embag.Types.Stores.DataStore;

import java.util.Map;

import idir.embag.DataModels.Metadata.EEventsDataKeys;

public interface IDataDelegate {
    public void add(Map<EEventsDataKeys,Object> data);
    public void importCollection(Map<EEventsDataKeys,Object> data);
    public void remove(Map<EEventsDataKeys,Object> data);
    public void update(Map<EEventsDataKeys,Object> data);
    public void search(Map<EEventsDataKeys,Object> data);
    public void load(Map<EEventsDataKeys,Object> data);
}
