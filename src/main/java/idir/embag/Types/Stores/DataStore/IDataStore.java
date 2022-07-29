package idir.embag.Types.Stores.DataStore;

import idir.embag.Types.Stores.Generics.IStore;
import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataStore extends IStore{
    public void add(StoreEvent event);
    public void remove(StoreEvent event);
    public void update(StoreEvent event);
    public void search(StoreEvent event);
    public void load(StoreEvent event);
}
