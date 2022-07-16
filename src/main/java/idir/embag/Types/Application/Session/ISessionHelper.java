package idir.embag.Types.Application.Session;

import idir.embag.Types.Stores.Generics.StoreEvent.StoreEvent;

public interface ISessionHelper {
    public void manageSessionWorkers();
    public void manageSessionGroups();
    public void refresh();
    public void notifyActive();
    public void closeSession();
    public void notifyEvent(StoreEvent event);
}
