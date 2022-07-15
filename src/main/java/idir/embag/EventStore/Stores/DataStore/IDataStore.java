package idir.embag.EventStore.Stores.DataStore;

import idir.embag.EventStore.Stores.Generics.IStore;
import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface IDataStore extends IStore{
    public void add(StoreEvent event);
    public void remove(StoreEvent event);
    public void update(StoreEvent event);
    public void get(StoreEvent event);
}
