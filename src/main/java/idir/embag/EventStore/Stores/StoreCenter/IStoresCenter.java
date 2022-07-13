package idir.embag.EventStore.Stores.StoreCenter;

import idir.embag.EventStore.Stores.Generics.StoreDispatch.StoreDispatch;

public interface IStoresCenter {
    public void dispatch(StoreDispatch action);
}
