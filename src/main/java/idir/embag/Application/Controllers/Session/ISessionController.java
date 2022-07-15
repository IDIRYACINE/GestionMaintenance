package idir.embag.Application.Controllers.Session;

import idir.embag.EventStore.Stores.Generics.StoreEvent.StoreEvent;

public interface ISessionController {

    public void manageSessionWorkers();
    public void manageSessionGroups();
    public void closeSession();
    public void notifyEvent(StoreEvent event);
    public void notifyActive();

}
